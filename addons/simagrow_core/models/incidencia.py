from odoo import models, fields


class SimagrowIncidencia(models.Model):
    _name = "simagrow.incidencia"
    _description = "Incidència SimaGrow"


    name = fields.Char(string="Descripció", required=True)
    espai_id = fields.Many2one("simagrow.espai", string="Espai")
    tipus = fields.Selection([
    ("llum", "Llum"),
    ("aigua", "Aigua"),
    ("residus", "Residus"),
    ("altres", "Altres")
    ], string="Tipus")
    estat = fields.Selection([
    ("nova", "Nova"),
    ("en_proces", "En procés"),
    ("resolta", "Resolta")
    ], string="Estat", default="nova")
    data = fields.Date(string="Data", default=fields.Date.today)
