#!/bin/bash
# --- CONFIGURACIÓN ---
NOMBRE_FINAL="proyecto"
ARCHIVO_PRO="configuracion.pro"

echo "--- Preparando $NOMBRE_FINAL ---"

# 1. Crear el .pro (o actualizarlo)
# Borramos el viejo para asegurar que se incluyan todos los .cpp nuevos
rm -f "$ARCHIVO_PRO"
qmake -project -o "$ARCHIVO_PRO"

# AÑADIMOS LOS MÓDULOS NECESARIOS
echo "TARGET = $NOMBRE_FINAL" >> "$ARCHIVO_PRO"
echo "QT += widgets network" >> "$ARCHIVO_PRO"  # <-- HE AÑADIDO 'network' AQUÍ
echo "Archivo $ARCHIVO_PRO configurado."

# 2. Limpiar rastros de compilaciones malas anteriores
make clean 2>/dev/null

# 3. Compilar y ejecutar
qmake "$ARCHIVO_PRO" && make -j$(nproc) && ./$NOMBRE_FINAL
