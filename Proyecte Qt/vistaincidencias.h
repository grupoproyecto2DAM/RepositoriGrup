#ifndef VISTAINCIDENCIAS_H
#define VISTAINCIDENCIAS_H

#include <QWidget>
#include <QTableWidget>
#include <QLineEdit>
#include <QPushButton>
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QLabel>
#include <QHeaderView>
#include <QFrame>

class VistaIncidencias : public QWidget {
    Q_OBJECT

public:
    explicit VistaIncidencias(QWidget *parent = nullptr);

signals:
    void altaSolicitada(QString titulo, QString descripcion);
    void logoutSolicitado();

private:
    QTableWidget *tabla;
    QLineEdit *le_titulo;
    QLineEdit *le_descripcion;
    QPushButton *btnAlta;
    QPushButton *btnVolver;
};

#endif
