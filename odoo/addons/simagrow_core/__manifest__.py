{
    "name": "SimaGrow Core",
    "version": "18.0.1.0.0",
    "summary": "Backend SIMAGROW (espais, incidències i API)",
    "description": "Gestió d'incidències i espais del projecte SIMAGROW. Inclou endpoints per a autenticació per token i gestió d'incidències.",
    "author": "SimaGrow",
    "category": "Education",
    "depends": ["base", "web"],
    "data": [
        "security/security.xml",
        "security/ir.model.access.csv",
        "views/incidencia_views.xml",
        "views/espai_views.xml",
        "views/usuari_views.xml",
        "views/api_token_views.xml",
        "views/menus.xml"
    ],
    "installable": True,
    "application": True
}
