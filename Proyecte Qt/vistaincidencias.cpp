#include "vistaincidencias.h"

VistaIncidencias::VistaIncidencias(QWidget *parent) : QWidget(parent) {
    QVBoxLayout *mainLayout = new QVBoxLayout(this);

    // Contenedor con estilo para que combine con el resto de la app
    QFrame *card = new QFrame();
    card->setObjectName("contenedorBlanco");
    card->setStyleSheet("background-color: rgba(255, 255, 255, 240); border-radius: 15px;");

    QVBoxLayout *cardLayout = new QVBoxLayout(card);
    cardLayout->setContentsMargins(30, 30, 30, 30);
    cardLayout->setSpacing(15);

    QLabel *titulo = new QLabel("<h2>Panel de Incidencias SimaGrow</h2>");

    // Formulario de entrada
    QHBoxLayout *formLayout = new QHBoxLayout();
    le_titulo = new QLineEdit();
    le_titulo->setPlaceholderText("Título...");

    le_descripcion = new QLineEdit();
    le_descripcion->setPlaceholderText("Descripción...");

    btnAlta = new QPushButton("DAR DE ALTA");
    btnAlta->setObjectName("btnPrincipal"); // Usa el estilo verde que definimos
    btnAlta->setMinimumHeight(40);

    formLayout->addWidget(le_titulo, 2);
    formLayout->addWidget(le_descripcion, 3);
    formLayout->addWidget(btnAlta, 1);

    // Tabla de visualización
    tabla = new QTableWidget(0, 3);
    tabla->setHorizontalHeaderLabels({"ID", "Título", "Estado"});
    tabla->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
    tabla->setStyleSheet("background: white; color: black;");

    btnVolver = new QPushButton("Cerrar Sesión");
    btnVolver->setObjectName("btnPlano");

    cardLayout->addWidget(titulo);
    cardLayout->addLayout(formLayout);
    cardLayout->addWidget(tabla);
    cardLayout->addWidget(btnVolver, 0, Qt::AlignRight);

    mainLayout->addWidget(card);

    // --- CONEXIONES ---
    connect(btnAlta, &QPushButton::clicked, [this]() {
        if(!le_titulo->text().isEmpty()) {
            emit altaSolicitada(le_titulo->text(), le_descripcion->text());
            le_titulo->clear();
            le_descripcion->clear();
        }
    });

    connect(btnVolver, &QPushButton::clicked, this, &VistaIncidencias::logoutSolicitado);
}
