#ifndef VENTANAPRINCIPAL_H
#define VENTANAPRINCIPAL_H

#include <QMainWindow>
#include <QStackedWidget>
#include <QLineEdit>
#include <QPushButton>
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QLabel>
#include <QMessageBox>
#include <QFrame>
#include <QTableWidget>
#include <QHeaderView>
#include <QRegularExpression>
#include <QJsonDocument>
#include <QJsonObject>
#include <QJsonArray>
#include <QDebug>
#include <QUrl>
#include <QUrlQuery>
#include <QComboBox>
#include <QGroupBox>
#include <QTimer>
#include <QDate>
#include <QIntValidator>
// --- AÑADIDO PARA RED ---
#include <QtNetwork/QNetworkAccessManager>
#include <QtNetwork/QNetworkReply>
#include <QtNetwork/QNetworkRequest>
// ------------------------
#include "incidencia.h"


class VentanaPrincipal : public QMainWindow {
    Q_OBJECT

public:
    VentanaPrincipal(QWidget *parent = nullptr);

private slots:
    void login();
    void registrarUsuario();
    void irARegistro();
    void irALogin();
    void crearIncidencia();
    void cargarIncidencias();
    void precargarUsuarios();

private:
    QWidget* crearContenedorCentrado(QWidget* tarjeta);

    QStackedWidget *stackedWidget;

    // --- AÑADIDO PARA RED ---
    QNetworkAccessManager *networkManager;
    // ------------------------

    // Campos Login/Registro
    QJsonArray listaUsuariosServer;
    QLineEdit *le_login_nia;
    QLineEdit *le_login_pass;
    QLineEdit *le_reg_nia;
    QLineEdit *le_reg_nombre;
    QLineEdit *le_reg_pass;
    QLineEdit *le_reg_pass_confirm;
    QLineEdit *le_reg_curso;
    QLineEdit *le_reg_materia;
    QLineEdit *le_inc_zona;
    QComboBox *cb_reg_rol;
    QComboBox *cb_inc_tipo;
    QComboBox *cb_inc_zona;
    int niaUsuarioLogueado;
    QLabel *lblUser;

    // Campos Incidencias (en Home)
    QLineEdit *le_inc_titulo;
    QLineEdit *le_inc_desc;
    QTableWidget *tablaIncidencias;

    // Vistas
    QWidget *crearVistaLogin();
    QWidget *crearVistaRegistro();
    QWidget *crearVistaHome();
};

#endif
