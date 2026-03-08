#include "ventanaprincipal.h"

VentanaPrincipal::VentanaPrincipal(QWidget *parent) : QMainWindow(parent) {
    networkManager = new QNetworkAccessManager(this);

    this->setWindowTitle("SimaGrow - Gestión de Incidencias");
    this->setMinimumSize(900, 700);

    stackedWidget = new QStackedWidget(this);

    stackedWidget->addWidget(crearVistaLogin());    // Index 0
    stackedWidget->addWidget(crearVistaRegistro()); // Index 1
    stackedWidget->addWidget(crearVistaHome());     // Index 2

    cargarIncidencias();

    setCentralWidget(stackedWidget);

    this->setStyleSheet(
        "QMainWindow { border-image: url('./Imagenes/fondo.png') 0 0 0 0 stretch stretch; }"
        "QFrame#contenedorBlanco { background-color: rgba(255, 255, 255, 240); border-radius: 20px; border: 1px solid #ffffff; }"
        "QLineEdit { padding: 10px; border: 1px solid #ccc; border-radius: 8px; background: white; }"
        "QPushButton#btnPrincipal { background-color: #2ecc71; color: white; border-radius: 8px; padding: 12px; font-weight: bold; }"
        "QPushButton#btnPrincipal:hover { background-color: #27ae60; }"
        "QPushButton#btnPlano { color: #2ecc71; border: none; background: transparent; text-decoration: underline; font-weight: bold; }"
        "QTableWidget { background: white; gridline-color: #eee; border-radius: 5px; }"
    );
}

QWidget* VentanaPrincipal::crearContenedorCentrado(QWidget* tarjeta) {
    QWidget *pagina = new QWidget();
    QVBoxLayout *vLayout = new QVBoxLayout(pagina);
    QHBoxLayout *hLayout = new QHBoxLayout();
    hLayout->addStretch(1); hLayout->addWidget(tarjeta); hLayout->addStretch(1);
    vLayout->addStretch(1); vLayout->addLayout(hLayout); vLayout->addStretch(1);
    return pagina;
}

QWidget* VentanaPrincipal::crearVistaLogin() {
    QFrame *card = new QFrame();
    card->setObjectName("contenedorBlanco");
    card->setFixedWidth(360);
    QVBoxLayout *layout = new QVBoxLayout(card);
    layout->setContentsMargins(30,30,30,30); layout->setSpacing(15);

    QLabel *logo = new QLabel("<h1 style='color: #2ecc71;'>SIMAGROW</h1><p>Introduce tu NIA</p>");
    logo->setAlignment(Qt::AlignCenter);

    le_login_nia = new QLineEdit();
    le_login_nia->setPlaceholderText("NIA");
    // Forzamos que solo se puedan meter números
    le_login_nia->setValidator(new QIntValidator(0, 99999999, this));

    le_login_pass = new QLineEdit();
    le_login_pass->setPlaceholderText("Contraseña");
    le_login_pass->setEchoMode(QLineEdit::Password);

    QPushButton *btn = new QPushButton("ENTRAR");
    btn->setObjectName("btnPrincipal");

    QPushButton *reg = new QPushButton("¿No tienes cuenta? Regístrate");
    reg->setObjectName("btnPlano");

    layout->addWidget(logo);
    layout->addWidget(le_login_nia);
    layout->addWidget(le_login_pass);
    layout->addWidget(btn);
    layout->addWidget(reg);

    // Conexiones
    connect(btn, &QPushButton::clicked, this, &VentanaPrincipal::login);
    connect(reg, &QPushButton::clicked, this, &VentanaPrincipal::irARegistro);

    // Intentar cargar usuarios del servidor nada más arrancar la vista
    QTimer::singleShot(500, this, [this](){ precargarUsuarios(); });

    return crearContenedorCentrado(card);
}

QWidget* VentanaPrincipal::crearVistaRegistro() {
    QFrame *card = new QFrame();
    card->setObjectName("contenedorBlanco");
    card->setFixedWidth(380);
    QVBoxLayout *layout = new QVBoxLayout(card);
    layout->setContentsMargins(30,30,30,30); layout->setSpacing(10);

    QLabel *logo = new QLabel("<h1 style='color: #2ecc71;'>SIMAGROW</h1><p>Registro de Usuario</p>");
    logo->setAlignment(Qt::AlignCenter);

    le_reg_nia = new QLineEdit();
    le_reg_nia->setPlaceholderText("Introduce tu NIA (Número)");
    le_reg_nia->setValidator(new QIntValidator(0, 99999999, this));

    le_reg_nombre = new QLineEdit();
    le_reg_nombre->setPlaceholderText("Nombre Completo");

    cb_reg_rol = new QComboBox();
    cb_reg_rol->addItems({"alumno", "profesor", "mantenimiento", "director"});
    cb_reg_rol->setStyleSheet("padding: 8px; border-radius: 5px; border: 1px solid #ccc;");

    le_reg_curso = new QLineEdit();
    le_reg_curso->setPlaceholderText("Curso (ej: 2º DAW)");

    QLabel *lblMateria = new QLabel("Materia:");
    le_reg_materia = new QLineEdit();
    le_reg_materia->setPlaceholderText("Materia (ej: Programación)");

    // --- CAMBIO AQUÍ: Solo se muestra si es PROFESOR ---
    connect(cb_reg_rol, &QComboBox::currentTextChanged, [this, lblMateria](const QString &rol){
        bool esProfesor = (rol == "profesor");
        lblMateria->setVisible(esProfesor);
        le_reg_materia->setVisible(esProfesor);
    });

    // Estado inicial (por defecto suele estar en "alumno", así que ocultamos)
    lblMateria->hide();
    le_reg_materia->hide();

    le_reg_pass = new QLineEdit();
    le_reg_pass->setPlaceholderText("Contraseña");
    le_reg_pass->setEchoMode(QLineEdit::Password);

    le_reg_pass_confirm = new QLineEdit();
    le_reg_pass_confirm->setPlaceholderText("Confirmar Contraseña");
    le_reg_pass_confirm->setEchoMode(QLineEdit::Password);

    QPushButton *btn = new QPushButton("REGISTRARSE");
    btn->setObjectName("btnPrincipal");
    QPushButton *back = new QPushButton("Volver al Login");
    back->setObjectName("btnPlano");

    layout->addWidget(logo);
    layout->addWidget(le_reg_nia);
    layout->addWidget(le_reg_nombre);
    layout->addWidget(new QLabel("Selecciona tu Rol:"));
    layout->addWidget(cb_reg_rol);
    layout->addWidget(le_reg_curso);
    layout->addWidget(lblMateria); // El label de materia
    layout->addWidget(le_reg_materia); // El campo de materia
    layout->addWidget(le_reg_pass);
    layout->addWidget(le_reg_pass_confirm);
    layout->addWidget(btn);
    layout->addWidget(back);

    connect(btn, &QPushButton::clicked, this, &VentanaPrincipal::registrarUsuario);
    connect(back, &QPushButton::clicked, this, &VentanaPrincipal::irALogin);

    return crearContenedorCentrado(card);
}

void VentanaPrincipal::registrarUsuario() {
    if (le_reg_nia->text().isEmpty() || le_reg_nombre->text().trimmed().isEmpty() || le_reg_pass->text().isEmpty()) {
        QMessageBox::warning(this, "Error", "NIA, Nombre y Contraseña son obligatorios.");
        return;
    }

    if (le_reg_pass->text() != le_reg_pass_confirm->text()) {
        QMessageBox::warning(this, "Error", "Las contraseñas no coinciden.");
        return;
    }

    QUrl url("http://20.111.17.43/simagrow/usuaris");
    QUrlQuery query;

    query.addQueryItem("nia", le_reg_nia->text().trimmed());
    query.addQueryItem("nombre", le_reg_nombre->text().trimmed());
    query.addQueryItem("password", le_reg_pass->text());
    query.addQueryItem("rol", cb_reg_rol->currentText());
    query.addQueryItem("curso", le_reg_curso->text().trimmed());

    // Si NO es profesor, el campo está oculto, así que enviamos N/A o vacío
    QString materia = le_reg_materia->isVisible() ? le_reg_materia->text().trimmed() : "Ninguna";
    query.addQueryItem("materia", materia);

    url.setQuery(query);

    QByteArray bodyData = "0";

    QNetworkRequest request(url);
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");

    QNetworkReply *reply = networkManager->post(request, bodyData);

    connect(reply, &QNetworkReply::finished, [this, reply]() {
        if (reply->error() == QNetworkReply::NoError) {
            QMessageBox::information(this, "Éxito", "Usuario registrado correctamente.");
            irALogin();
        } else {
            qDebug() << "Error:" << reply->readAll();
            QMessageBox::critical(this, "Error", "Fallo al registrar. Comprueba los datos.");
        }
        reply->deleteLater();
    });
}

QWidget* VentanaPrincipal::crearVistaHome() {
    QFrame *card = new QFrame();
    card->setObjectName("contenedorBlanco");
    card->setMinimumWidth(1000);
    QVBoxLayout *layout = new QVBoxLayout(card);
    layout->setContentsMargins(30, 30, 30, 30);
    layout->setSpacing(15);

    // 1. Cabecera con Información de Usuario
    QHBoxLayout *headerLayout = new QHBoxLayout();

    QVBoxLayout *tituloLayout = new QVBoxLayout();
    tituloLayout->addWidget(new QLabel("<h2>Panel de Control - SimaGrow</h2>"));

    // Mostramos el NIA del usuario que se ha logueado
    this->lblUser = new QLabel("👤 Usuario conectado (NIA): <b>Esperando login...</b>");
    this->lblUser->setStyleSheet("color: #7f8c8d; font-size: 13px;");
    tituloLayout->addWidget(this->lblUser);

    headerLayout->addLayout(tituloLayout);

    QPushButton *btnRefrescar = new QPushButton("🔄 Refrescar");
    btnRefrescar->setFixedWidth(120);
    btnRefrescar->setCursor(Qt::PointingHandCursor);

    headerLayout->addStretch();
    headerLayout->addWidget(btnRefrescar);
    layout->addLayout(headerLayout);

    // 2. Formulario de Alta (Usando los campos del Struct Incidencia)
    QGroupBox *grupoAlta = new QGroupBox("Registrar Nueva Incidencia");
    grupoAlta->setStyleSheet("QGroupBox { font-weight: bold; border: 1px solid #dcdde1; border-radius: 8px; margin-top: 10px; padding-top: 15px; }");
    QVBoxLayout *vForm = new QVBoxLayout(grupoAlta);

    // Fila 1: Título (Name) y Descripción (Description)
    QHBoxLayout *fila1 = new QHBoxLayout();
    le_inc_titulo = new QLineEdit();
    le_inc_titulo->setPlaceholderText("Título de la incidencia (ej. Ratas mutantes)...");

    le_inc_desc = new QLineEdit();
    le_inc_desc->setPlaceholderText("Descripción detallada del problema...");

    fila1->addWidget(le_inc_titulo, 1);
    fila1->addWidget(le_inc_desc, 2);
    vForm->addLayout(fila1);

    // Fila 2: Tipo, Zona y Botón de envío
    QHBoxLayout *fila2 = new QHBoxLayout();

    cb_inc_tipo = new QComboBox();
    cb_inc_tipo->addItems({"Hardware", "Software", "Redes", "Mantenimiento", "Sanidad", "Otros"});
    cb_inc_tipo->setMinimumWidth(150);

    le_inc_zona = new QLineEdit();
    le_inc_zona->setPlaceholderText("Ubicación (ej. Sector 7-G, Aula 202)...");

    QPushButton *btnAlta = new QPushButton("ENVIAR INCIDENCIA");
    btnAlta->setObjectName("btnPrincipal");
    btnAlta->setFixedWidth(200);
    btnAlta->setCursor(Qt::PointingHandCursor);

    fila2->addWidget(new QLabel("Tipo:"));
    fila2->addWidget(cb_inc_tipo);
    fila2->addSpacing(20);
    fila2->addWidget(new QLabel("Zona:"));
    fila2->addWidget(le_inc_zona, 1);
    fila2->addSpacing(20);
    fila2->addWidget(btnAlta);
    vForm->addLayout(fila2);

    layout->addWidget(grupoAlta);

    // 3. Tabla de Incidencias (7 Columnas: ID, Fecha, Nombre, Tipo, Zona, Estado, Alumno)
    tablaIncidencias = new QTableWidget(0, 7);
    tablaIncidencias->setHorizontalHeaderLabels({"ID", "Fecha", "Nombre", "Tipo", "Zona", "Estado", "Alumno"});
    tablaIncidencias->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
    tablaIncidencias->setAlternatingRowColors(true); // Hace la tabla más legible
    tablaIncidencias->setSelectionBehavior(QAbstractItemView::SelectRows);
    layout->addWidget(tablaIncidencias);

    // 4. Barra inferior con Logout
    QHBoxLayout *footerLayout = new QHBoxLayout();

    QLabel *lblStatus = new QLabel("✅ Sistema conectado al servidor");
    lblStatus->setStyleSheet("color: #27ae60;");

    QPushButton *logout = new QPushButton("Cerrar Sesión");
    logout->setObjectName("btnPlano");
    logout->setCursor(Qt::PointingHandCursor);

    footerLayout->addWidget(lblStatus);
    footerLayout->addStretch();
    footerLayout->addWidget(logout);
    layout->addLayout(footerLayout);

    // --- Conexiones ---
    connect(btnAlta, &QPushButton::clicked, this, &VentanaPrincipal::crearIncidencia);
    connect(btnRefrescar, &QPushButton::clicked, this, &VentanaPrincipal::cargarIncidencias);
    connect(logout, &QPushButton::clicked, this, &VentanaPrincipal::irALogin);

    return crearContenedorCentrado(card);
}

void VentanaPrincipal::cargarIncidencias() {
    QUrl url("http://20.111.17.43/simagrow/incidencias");
    QNetworkReply *reply = networkManager->get(QNetworkRequest(url));

    connect(reply, &QNetworkReply::finished, [this, reply]() {
        if (reply->error() == QNetworkReply::NoError) {
            QByteArray data = reply->readAll();
            QJsonDocument doc = QJsonDocument::fromJson(data);
            QJsonArray incidencias = doc.array();

            tablaIncidencias->setRowCount(0);

            for (int i = 0; i < incidencias.size(); ++i) {
                QJsonObject inc = incidencias[i].toObject();

                // --- PROCESADO Y LIMPIEZA DE DATOS ---
                // Quitamos las comillas extra si el servidor las manda: "\"texto\"" -> "texto"
                auto limpiar = [](QString s) {
                    s = s.trimmed();
                    if (s.startsWith("\"") && s.endsWith("\"")) s = s.mid(1, s.length() - 2);
                    return s;
                };

                QString nombre = limpiar(inc["nombre"].toString());
                QString estado = limpiar(inc["estado"].toString());
                QString tipo   = limpiar(inc["tipo"].toString());
                QString zona   = limpiar(inc["zona"].toString());
                QString fecha  = limpiar(inc["fecha"].toString());

                // --- FILTRO ANTI-BASURA MEJORADO ---
                // Si el nombre contiene una llave, o es "string", o está vacío, lo ignoramos
                if (nombre.contains("{") || nombre.contains("}") ||
                    nombre.toLower() == "string" || nombre.isEmpty()) {
                    continue;
                    }

                    int row = tablaIncidencias->rowCount();
                    tablaIncidencias->insertRow(row);

                    // 1. ID
                    tablaIncidencias->setItem(row, 0, new QTableWidgetItem(QString::number(inc["id"].toInt())));

                    // 2. Fecha
                    tablaIncidencias->setItem(row, 1, new QTableWidgetItem(fecha));

                    // 3. Nombre
                    tablaIncidencias->setItem(row, 2, new QTableWidgetItem(nombre));

                    // 4. Tipo
                    tablaIncidencias->setItem(row, 3, new QTableWidgetItem(tipo));

                    // 5. Zona
                    tablaIncidencias->setItem(row, 4, new QTableWidgetItem(zona));

                    // 6. Estado (con lógica de color)
                    QTableWidgetItem *itemEstado = new QTableWidgetItem(estado);
                    if(estado.toUpper() == "ABIERTA" || tipo.toUpper() == "CRÍTICA") {
                        itemEstado->setForeground(Qt::red);
                        itemEstado->setFont(QFont("Arial", -1, QFont::Bold));
                    }
                    tablaIncidencias->setItem(row, 5, itemEstado);

                    // 7. Alumno (NIA)
                    int nia = inc["alumnoNIA"].toInt();
                    tablaIncidencias->setItem(row, 6, new QTableWidgetItem(nia > 0 ? QString::number(nia) : "-"));
            }
        } else {
            qDebug() << "Error al cargar:" << reply->errorString();
        }
        reply->deleteLater();
    });
}

void VentanaPrincipal::crearIncidencia() {
    if(le_inc_titulo->text().trimmed().isEmpty()) return;

    // 1. CONFIGURAR LA URL CON QUERY PARAMETERS
    QUrl url("http://20.111.17.43/simagrow/incidencias");
    QUrlQuery query;

    query.addQueryItem("tipo", cb_inc_tipo->currentText());
    query.addQueryItem("zona", le_inc_zona->text().trimmed());
    query.addQueryItem("descripcion", le_inc_desc->text().trimmed());
    query.addQueryItem("fecha", QDate::currentDate().toString(Qt::ISODate));
    query.addQueryItem("alumnoNIA", QString::number(this->niaUsuarioLogueado));
    query.addQueryItem("estado", "ABIERTA");

    url.setQuery(query);

    // 2. EL CUERPO DEL POST (Request Body)
    QByteArray bodyData = "\"" + le_inc_titulo->text().trimmed().toUtf8() + "\"";

    // 3. CONFIGURAR REQUEST
    QNetworkRequest request(url);
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");

    // 4. ENVIAR
    QNetworkReply *reply = networkManager->post(request, bodyData);

    connect(reply, &QNetworkReply::finished, [this, reply]() {
        if (reply->error() == QNetworkReply::NoError) {
            le_inc_titulo->clear();
            le_inc_desc->clear();
            le_inc_zona->clear();
            cargarIncidencias();
            QMessageBox::information(this, "SimaGrow", "¡Incidencia creada correctamente!");
        } else {
            qDebug() << "Fallo:" << reply->readAll();
        }
        reply->deleteLater();
    });
}

void VentanaPrincipal::precargarUsuarios() {
    // URL del endpoint que devuelve el JSON de usuarios
    QUrl url("http://20.111.17.43/simagrow/usuaris");
    QNetworkReply *reply = networkManager->get(QNetworkRequest(url));

    connect(reply, &QNetworkReply::finished, [this, reply]() {
        if (reply->error() == QNetworkReply::NoError) {
            QJsonDocument doc = QJsonDocument::fromJson(reply->readAll());
            listaUsuariosServer = doc.array();
            qDebug() << "Sincronizados " << listaUsuariosServer.size() << " usuarios.";
        } else {
            qDebug() << "Error precargando usuarios:" << reply->errorString();
        }
        reply->deleteLater();
    });
}

void VentanaPrincipal::login() {
    QString niaTexto = le_login_nia->text().trimmed();
    QString passIngresada = le_login_pass->text();

    if (niaTexto.isEmpty() || passIngresada.isEmpty()) {
        QMessageBox::warning(this, "SimaGrow", "Introduce NIA y contraseña.");
        return;
    }

    QUrl url("http://20.111.17.43/simagrow/usuaris");
    QNetworkReply *reply = networkManager->get(QNetworkRequest(url));

    connect(reply, &QNetworkReply::finished, [this, reply, niaTexto, passIngresada]() {
        if (reply->error() == QNetworkReply::NoError) {
            QByteArray data = reply->readAll();
            QJsonDocument doc = QJsonDocument::fromJson(data);
            QJsonArray usuarios = doc.array();

            bool encontrado = false;
            QString nombreEncontrado = "";

            for (const QJsonValue &v : usuarios) {
                QJsonObject u = v.toObject();
                if (u["nia"].toInt() == niaTexto.toInt() && u["password"].toString() == passIngresada) {
                    encontrado = true;
                    nombreEncontrado = u["nombre"].toString();
                    break;
                }
            }

            if (encontrado) {
                // 1. Guardamos el NIA real
                this->niaUsuarioLogueado = niaTexto.toInt();

                // --- LÍNEA AÑADIDA: Actualizamos el label de la vista Home ---
                // Suponiendo que lblUser es el label que creamos en crearVistaHome
                if (this->lblUser) {
                    this->lblUser->setText(QString("👤 Usuario conectado (NIA): <b>%1</b>").arg(this->niaUsuarioLogueado));
                }

                QMessageBox::information(this, "SimaGrow", "¡Bienvenido/a " + nombreEncontrado + "!");

                stackedWidget->setCurrentIndex(2);
                cargarIncidencias();

                le_login_nia->clear();
                le_login_pass->clear();
            } else {
                QMessageBox::critical(this, "Error de Acceso", "El NIA o la contraseña no constan en el sistema.");
            }
        } else {
            QMessageBox::critical(this, "Error de Red", "No se pudo conectar con el servidor.");
        }
        reply->deleteLater();
    });
}

void VentanaPrincipal::irARegistro() { stackedWidget->setCurrentIndex(1); }
void VentanaPrincipal::irALogin() { stackedWidget->setCurrentIndex(0); }
