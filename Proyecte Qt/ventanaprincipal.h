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
#include <QtNetwork/QNetworkAccessManager>
#include <QtNetwork/QNetworkReply>
#include <QtNetwork/QNetworkRequest>

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
    QWidget* crearVistaLogin();
    QWidget* crearVistaRegistro();
    QWidget* crearVistaHome();

    QStackedWidget *stackedWidget;
    QNetworkAccessManager *networkManager;

    // Datos de Sesión
    QJsonArray listaUsuariosServer;
    int niaUsuarioLogueado;
    QString rolUsuarioLogueado;

    QLineEdit *le_login_nia, *le_login_pass;
    QLineEdit *le_reg_nia, *le_reg_nombre, *le_reg_pass, *le_reg_pass_confirm, *le_reg_curso, *le_reg_materia;
    QLineEdit *le_inc_titulo, *le_inc_desc;
    QComboBox *cb_reg_rol, *cb_inc_tipo, *cb_inc_zona;
    QTableWidget *tablaIncidencias;
    QLabel *lblUser;
};

#endif
