from odoo import models, fields
import secrets
from datetime import timedelta


class SimagrowApiToken(models.Model):
    _name = "simagrow.api_token"
    _description = "API Token SimaGrow"
    _rec_name = "token"

    token = fields.Char(required=True, index=True)
    usuari_id = fields.Many2one("simagrow.usuari", required=True, ondelete="cascade")
    created_at = fields.Datetime(default=fields.Datetime.now, required=True)
    expires_at = fields.Datetime()

    active = fields.Boolean(default=True)

    _sql_constraints = [
        ("token_unique", "unique(token)", "Token duplicat."),
    ]

    @staticmethod
    def generate_token() -> str:
        # 48 bytes -> 64 chars urlsafe aprox
        return secrets.token_urlsafe(48)

    def is_valid(self) -> bool:
        self.ensure_one()
        if not self.active:
            return False
        if self.expires_at and fields.Datetime.now() >= self.expires_at:
            return False
        return True

    @classmethod
    def create_for_user(cls, env, usuari, ttl_hours: int | None = 24):
        token = cls.generate_token()
        vals = {
            "token": token,
            "usuari_id": usuari.id,
        }
        if ttl_hours:
            vals["expires_at"] = fields.Datetime.now() + timedelta(hours=ttl_hours)
        rec = env["simagrow.api_token"].sudo().create(vals)
        return rec
