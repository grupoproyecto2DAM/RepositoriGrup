from odoo import models, fields


class SimagrowIndicador(models.Model):
    _name = "simagrow.indicador"
    _description = "Indicador de sostenibilitat"


    name = fields.Char(string="Nom", required=True)
    categoria = fields.Selection([
    ("energia", "Energia"),
    ("aigua", "Aigua"),
    ("residus", "Residus"),
    ("mobilitat", "Mobilitat")
    ], string="Categoria")
    valor = fields.Float(string="Valor")
    unitat = fields.Char(string="Unitat")
    data = fields.Date(string="Data")