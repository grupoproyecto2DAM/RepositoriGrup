#ifndef INCIDENCIA_H
#define INCIDENCIA_H

#include <QString>
#include <QJsonObject>
#include <QJsonValue>
#include <QJsonDocument>

struct Incidencia {
    int id;
    QString nombre;
    QString tipo;
    QString zona;
    QString descripcion;
    QString fecha;
    int alumnoNIA;
    QString estado;

    // Para enviar al servidor (POST) - Formato limpio para Spring Boot
    QJsonObject toJson() const {
        QJsonObject json;
        if (id > 0) json.insert("id", id);

        json.insert("nombre", nombre);
        json.insert("descripcion", descripcion);
        json.insert("tipo", tipo);
        json.insert("zona", zona);
        json.insert("fecha", fecha);
        json.insert("alumnoNIA", alumnoNIA);
        json.insert("estado", estado);

        return json;
    }

    // Para leer del servidor (GET) - Lógica de limpieza y reparación mejorada
    static Incidencia fromJson(const QJsonObject &json) {
        Incidencia inc;

        // Función lambda auxiliar para limpiar comillas y espacios extra
        auto limpiarStr = [](const QJsonValue &val) {
            QString s = val.toString().trimmed();
            if (s.startsWith("\"") && s.endsWith("\"")) {
                s = s.mid(1, s.length() - 2);
            }
            return s;
        };

        inc.id = json.value("id").toInt();
        QString nombreRaw = json.value("nombre").toString().trimmed();

        // 1. AUTO-REPARACIÓN: Si el campo 'nombre' contiene el JSON completo por error del servidor
        if (nombreRaw.startsWith("{")) {
            QJsonDocument docInterno = QJsonDocument::fromJson(nombreRaw.toUtf8());
            if (!docInterno.isNull()) {
                QJsonObject obj = docInterno.object();
                inc.nombre = limpiarStr(obj.value("nombre"));
                inc.descripcion = limpiarStr(obj.value("descripcion"));
                inc.tipo = limpiarStr(obj.value("tipo"));
                inc.zona = limpiarStr(obj.value("zona"));
                inc.fecha = limpiarStr(obj.value("fecha"));
                inc.alumnoNIA = obj.value("alumnoNIA").toInt();
                inc.estado = limpiarStr(obj.value("estado"));
                return inc;
            }
        }

        // 2. CARGA NORMAL: Si los datos vienen correctamente repartidos en sus campos
        inc.nombre = limpiarStr(json.value("nombre"));
        inc.descripcion = limpiarStr(json.value("descripcion"));
        inc.tipo = limpiarStr(json.value("tipo"));
        inc.zona = limpiarStr(json.value("zona"));
        inc.fecha = limpiarStr(json.value("fecha"));
        inc.alumnoNIA = json.value("alumnoNIA").toInt();
        inc.estado = limpiarStr(json.value("estado"));

        return inc;
    }

    // Validación mejorada para filtrar la "basura" en la tabla
    bool esValida() const {
        QString n = nombre.toLower();
        return !n.isEmpty() && n != "string" && !n.startsWith("{");
    }
};

#endif // INCIDENCIA_H
