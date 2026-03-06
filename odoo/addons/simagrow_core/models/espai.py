from odoo import models, fields


class SimagrowEspai(models.Model):
    _name = "simagrow.espai"
    _description = "Espai del centre"

    name = fields.Char(string="Nom", required=True)

    # En el document acordat es diu 'zona' (enum). El model existent ja tenia 'tipus';
    # fem servir 'zona' com a camp de negoci i mantenim l'antic 'tipus' fora.
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
    ], string="Zona", required=True)

    active = fields.Boolean(default=True)
