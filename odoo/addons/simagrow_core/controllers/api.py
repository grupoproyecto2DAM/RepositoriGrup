import json
from odoo import http
from odoo.http import request
from werkzeug.wrappers import Response


def _json_response(payload, status=200):
    return Response(
        json.dumps(payload, ensure_ascii=False, default=str),
        status=status,
        content_type="application/json; charset=utf-8",
    )


def _get_json_body():
    try:
        raw = request.httprequest.data
        if not raw:
            return {}
        return json.loads(raw.decode("utf-8"))
    except Exception:
        return {}


def _get_bearer_token():
    auth = request.httprequest.headers.get("Authorization", "")
    if auth.lower().startswith("bearer "):
        return auth[7:].strip()
    return request.httprequest.headers.get("X-Api-Token", "").strip()


def _require_token():
    token_value = _get_bearer_token()
    if not token_value:
        return None, _json_response({"error": "Missing token"}, status=401)

    token = request.env["simagrow.api_token"].sudo().search([
        ("token", "=", token_value),
        ("active", "=", True),
    ], limit=1)

    if not token or not token.is_valid():
        return None, _json_response({"error": "Invalid or expired token"}, status=401)

    return token, None


class SimagrowApi(http.Controller):

    # ---------- AUTH ----------
    @http.route("/api/auth/register", type="http", auth="public", methods=["POST"], csrf=False)
    def register(self, **kwargs):
        data = _get_json_body()
        required = ["nombre", "nia", "password", "rol"]
        missing = [k for k in required if not data.get(k)]
        if missing:
            return _json_response({"error": f"Missing fields: {', '.join(missing)}"}, status=400)

        rol = data.get("rol")
        if rol not in ("ALUMNO", "PROFESOR"):
            return _json_response({"error": "rol debe ser ALUMNO o PROFESOR"}, status=400)

        nia = str(data.get("nia")).strip()

        try:
            usuari = request.env["simagrow.usuari"].sudo().create({
                "nombre": data.get("nombre"),
                "nia": nia,
                "password": data.get("password"),
                "rol": rol,
                "curso": data.get("curso") or False,
                "materia": data.get("materia") or False,
            })
        except Exception as e:
            return _json_response({"error": str(e)}, status=400)

        token_rec = request.env["simagrow.api_token"].sudo().create_for_user(request.env, usuari, ttl_hours=24)
        return _json_response({
            "token": token_rec.token,
            "usuari": {
                "nombre": usuari.nombre,
                "nia": usuari.nia,
                "rol": usuari.rol,
                "curso": usuari.curso,
                "materia": usuari.materia,
            }
        }, status=201)

    @http.route("/api/auth/login", type="http", auth="public", methods=["POST"], csrf=False)
    def login(self, **kwargs):
        data = _get_json_body()
        nia = (data.get("nia") or "").strip()
        password = data.get("password") or ""

        if not nia or not password:
            return _json_response({"error": "nia and password are required"}, status=400)

        usuari = request.env["simagrow.usuari"].sudo().search([("nia", "=", nia)], limit=1)
        if not usuari or not usuari.check_password(password):
            return _json_response({"error": "Invalid credentials"}, status=401)

        token_rec = request.env["simagrow.api_token"].sudo().create_for_user(request.env, usuari, ttl_hours=24)
        return _json_response({
            "token": token_rec.token,
            "usuari": {
                "nombre": usuari.nombre,
                "nia": usuari.nia,
                "rol": usuari.rol,
                "curso": usuari.curso,
                "materia": usuari.materia,
            }
        })

    @http.route("/api/me", type="http", auth="public", methods=["GET"], csrf=False)
    def me(self, **kwargs):
        token, err = _require_token()
        if err:
            return err

        u = token.usuari_id
        return _json_response({
            "nombre": u.nombre,
            "nia": u.nia,
            "rol": u.rol,
            "curso": u.curso,
            "materia": u.materia,
        })

    # ---------- ESPAIS ----------
    @http.route("/api/espais", type="http", auth="public", methods=["GET"], csrf=False)
    def espais_list(self, **kwargs):
        token, err = _require_token()
        if err:
            return err

        recs = request.env["simagrow.espai"].sudo().search([("active", "=", True)], order="name asc")
        return _json_response([{
            "id": r.id,
            "name": r.name,
            "zona": r.zona,
            "active": r.active,
        } for r in recs])

    # ---------- INCIDENCIES ----------
    @http.route("/api/incidencies", type="http", auth="public", methods=["GET"], csrf=False)
    def incidencies_list(self, **kwargs):
        token, err = _require_token()
        if err:
            return err

        u = token.usuari_id

        estado = request.httprequest.args.get("estado")
        tipo = request.httprequest.args.get("tipo")
        zona = request.httprequest.args.get("zona")
        mine = request.httprequest.args.get("mine") in ("1", "true", "True")

        domain = []
        if estado:
            domain.append(("estado", "=", estado))
        if tipo:
            domain.append(("tipo", "=", tipo))
        if zona:
            domain.append(("zona", "=", zona))

        if u.rol == "ALUMNO" or mine:
            domain.append(("alumno_id", "=", u.id))

        recs = request.env["simagrow.incidencia"].sudo().search(domain, order="fecha desc", limit=200)
        return _json_response([{
            "id": r.id,
            "titulo": r.titulo,
            "tipo": r.tipo,
            "zona": r.zona,
            "descripcion": r.descripcion,
            "fecha": r.fecha,
            "estado": r.estado,
            "alumnoNia": r.alumno_nia,
            "espaiId": r.espai_id.id if r.espai_id else None,
            "profesorNia": r.profesor_id.nia if r.profesor_id else None,
        } for r in recs])

    @http.route("/api/incidencies", type="http", auth="public", methods=["POST"], csrf=False)
    def incidencies_create(self, **kwargs):
        token, err = _require_token()
        if err:
            return err

        u = token.usuari_id

        try:
            data = _get_json_body()
            required = ["titulo", "tipo", "zona"]
            missing = [k for k in required if not data.get(k)]
            if missing:
                return _json_response({"error": f"Missing fields: {', '.join(missing)}"}, status=400)

            alumno = u
            if u.rol == "PROFESOR" and data.get("alumnoNia"):
                alumno = request.env["simagrow.usuari"].sudo().search([("nia", "=", data["alumnoNia"])], limit=1)
                if not alumno:
                    return _json_response({"error": "alumnoNia not found"}, status=400)

            vals = {
                "titulo": data["titulo"],
                "tipo": data["tipo"],
                "zona": data["zona"],
                "descripcion": data.get("descripcion") or False,
                "estado": data.get("estado") or "ABIERTA",
                "alumno_id": alumno.id,
                "profesor_id": u.id if u.rol == "PROFESOR" else False,
            }

            if data.get("espaiId"):
                espai = request.env["simagrow.espai"].sudo().browse(int(data["espaiId"])).exists()
                if not espai:
                    return _json_response({"error": "espaiId not found"}, status=400)
                vals["espai_id"] = espai.id

            rec = request.env["simagrow.incidencia"].sudo().create(vals)

            return _json_response({
                "id": rec.id,
                "titulo": rec.titulo,
                "tipo": rec.tipo,
                "zona": rec.zona,
                "descripcion": rec.descripcion,
                "fecha": rec.fecha,
                "estado": rec.estado,
                "alumnoNia": rec.alumno_nia,
                "espaiId": rec.espai_id.id if rec.espai_id else None,
                "profesorNia": rec.profesor_id.nia if rec.profesor_id else None,
            }, status=201)

        except Exception as e:
            return _json_response({"debug_error": str(e)}, status=500)

    @http.route("/api/incidencies/<int:inc_id>", type="http", auth="public", methods=["GET"], csrf=False)
    def incidencies_get(self, inc_id, **kwargs):
        token, err = _require_token()
        if err:
            return err

        u = token.usuari_id
        rec = request.env["simagrow.incidencia"].sudo().browse(inc_id).exists()

        if not rec:
            return _json_response({"error": "Not found"}, status=404)

        if u.rol == "ALUMNO" and rec.alumno_id.id != u.id:
            return _json_response({"error": "Forbidden"}, status=403)

        return _json_response({
            "id": rec.id,
            "titulo": rec.titulo,
            "tipo": rec.tipo,
            "zona": rec.zona,
            "descripcion": rec.descripcion,
            "fecha": rec.fecha,
            "estado": rec.estado,
            "alumnoNia": rec.alumno_nia,
            "espaiId": rec.espai_id.id if rec.espai_id else None,
            "profesorNia": rec.profesor_id.nia if rec.profesor_id else None,
        })

    @http.route("/api/incidencies/<int:inc_id>", type="http", auth="public", methods=["PATCH"], csrf=False)
    def incidencies_patch(self, inc_id, **kwargs):
        token, err = _require_token()
        if err:
            return err

        u = token.usuari_id
        rec = request.env["simagrow.incidencia"].sudo().browse(inc_id).exists()

        if not rec:
            return _json_response({"error": "Not found"}, status=404)

        if u.rol != "PROFESOR":
            return _json_response({"error": "Forbidden"}, status=403)

        try:
            data = _get_json_body()
            vals = {}

            if "estado" in data and data["estado"]:
                vals["estado"] = data["estado"]
            if "descripcion" in data:
                vals["descripcion"] = data["descripcion"] or False
            if "titulo" in data and data["titulo"]:
                vals["titulo"] = data["titulo"]
            if "tipo" in data and data["tipo"]:
                vals["tipo"] = data["tipo"]
            if "zona" in data and data["zona"]:
                vals["zona"] = data["zona"]
            if "espaiId" in data:
                if data["espaiId"]:
                    espai = request.env["simagrow.espai"].sudo().browse(int(data["espaiId"])).exists()
                    if not espai:
                        return _json_response({"error": "espaiId not found"}, status=400)
                    vals["espai_id"] = espai.id
                else:
                    vals["espai_id"] = False

            if not vals:
                return _json_response({"error": "Nothing to update"}, status=400)

            vals["profesor_id"] = u.id
            rec.write(vals)

            return _json_response({
                "id": rec.id,
                "titulo": rec.titulo,
                "tipo": rec.tipo,
                "zona": rec.zona,
                "descripcion": rec.descripcion,
                "fecha": rec.fecha,
                "estado": rec.estado,
                "alumnoNia": rec.alumno_nia,
                "espaiId": rec.espai_id.id if rec.espai_id else None,
                "profesorNia": rec.profesor_id.nia if rec.profesor_id else None,
            })

        except Exception as e:
            return _json_response({"debug_error": str(e)}, status=500)