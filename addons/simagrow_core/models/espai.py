from odoo import models, fields


class SimagrowEspai(models.Model):
    _name = "simagrow.espai"
    _description = "Espai del centre"


    name = fields.Char(string="Nom", required=True)
    tipus = fields.Selection([
    ("aula", "Aula"),
    ("lavabo", "Lavabo"),
    ("pati", "Pati"),
    ("altres", "Altres")
    ], string="Tipus", required=True)