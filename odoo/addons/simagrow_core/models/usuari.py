from odoo import models, fields, api
from odoo.exceptions import ValidationError
from werkzeug.security import generate_password_hash, check_password_hash


class SimagrowUsuari(models.Model):
    _name = "simagrow.usuari"
    _description = "Usuari SimaGrow"
    _rec_name = "nia"

    nombre = fields.Char(required=True)
    nia = fields.Char(required=True, index=True)
    # Guardem hash, NO password en pla
    password_hash = fields.Char(string="Password (hash)")
    rol = fields.Selection([
        ("ALUMNO", "Alumno"),
        ("PROFESOR", "Profesor"),
    ], required=True, default="ALUMNO")
    curso = fields.Char()
    materia = fields.Char()

    password = fields.Char(string="Contraseña", store=False)

    _sql_constraints = [
        ("nia_unique", "unique(nia)", "Ja existeix un usuari amb aquest NIA."),
    ]

    def set_password(self, raw_password: str):
        if not raw_password:
            raise ValidationError("La contrasenya no pot estar buida.")
        self.password_hash = generate_password_hash(raw_password)

    def check_password(self, raw_password: str) -> bool:
        if not self.password_hash:
            return False
        return check_password_hash(self.password_hash, raw_password)
    
    @api.model
    def create(self, vals):
        password = vals.pop("password", None)

        # Si llega password, lo convertimos a hash en vals antes de crear
        if password:
            vals["password_hash"] = generate_password_hash(password)

        rec = super().create(vals)

        # Si no llegó password, opcionalmente puedes impedir crear usuarios sin contraseña
        if not rec.password_hash:
            raise ValidationError("Has d'indicar una contrasenya (camp 'password') per crear l'usuari.")

        return rec


    def write(self, vals):
        password = vals.pop("password", None)
        if password:
            vals["password_hash"] = generate_password_hash(password)
        return super().write(vals)
