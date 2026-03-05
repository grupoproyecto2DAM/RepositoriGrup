from odoo import models, fields


class SimagrowIncidencia(models.Model):
    _name = "simagrow.incidencia"
    _description = "Incidència SimaGrow"
    _order = "fecha desc, id desc"

    titulo = fields.Char(required=True)
    tipo = fields.Selection([
        ("ELECTRICIDAD", "Electricidad"),
        ("FONTANERIA", "Fontanería"),
        ("LIMPIEZA", "Limpieza"),
        ("CLIMATIZACION", "Climatización"),
        ("MOBILIARIO", "Mobiliario"),
        ("IT", "IT"),
        ("SEGURIDAD", "Seguridad"),
        ("OTRO", "Otro"),
    ], required=True)

    zona = fields.Selection([
        ("EDIFICIO", "Edifici"),
        ("PLANTA", "Planta"),
        ("AULA", "Aula"),
        ("LABORATORIO", "Laboratori"),
        ("BANO", "Bany"),
        ("PASILLO", "Passadís"),
        ("PATIO", "Pati"),
        ("CONSERJERIA", "Consergeria"),
        ("OTRO", "Altres"),
    ], required=True)

    descripcion = fields.Text()
    fecha = fields.Datetime(default=fields.Datetime.now, required=True)

    estado = fields.Selection([
        ("ABIERTA", "Abierta"),
        ("EN_PROCESO", "En proceso"),
        ("RESUELTA", "Resuelta"),
        ("CERRADA", "Cerrada"),
        ("RECHAZADA", "Rechazada"),
    ], default="ABIERTA", required=True)

    espai_id = fields.Many2one("simagrow.espai", string="Espai", ondelete="set null")
    alumno_id = fields.Many2one("simagrow.usuari", string="Alumno", ondelete="restrict")
    alumno_nia = fields.Char(related="alumno_id.nia", string="Alumno NIA", store=True, readonly=True)

    # Per a gestió de professor: qui la resol / actualitza (opcional)
    profesor_id = fields.Many2one("simagrow.usuari", string="Profesor", ondelete="set null")
