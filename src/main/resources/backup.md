# Sistema de Inscripción - Prácticas Cruz Roja

Este proyecto cuenta con una arquitectura de perfiles para desarrollo local (`dev`) y producción (`prod` en Supabase).

## 🚀 Cómo cambiar de entorno
En el archivo `src/main/resources/application.properties`, modifica la línea:
* `spring.profiles.active=dev` -> Corre en tu computadora usando Postgres local.
* `spring.profiles.active=prod` -> Se conecta directamente a la nube en Supabase.

---

## 💾 Instrucciones para realizar el Backup (Migración de Nube a Local)

Una vez finalizada la inscripción de los estudiantes en la nube, sigue estos pasos para descargar toda la información a tu base de datos local y evitar el pausado por inactividad de Supabase.

### Paso 1: Limpiar la base de datos local (Opcional)
Si hiciste pruebas locales y quieres una copia exacta y limpia de la nube, ve a pgAdmin, elimina la base de datos `cruzroja` y vuélvela a crear vacía.

### Paso 2: Ejecutar el comando en Git Bash
Abre la consola de **Git Bash** e introduce la siguiente línea de comandos. Reemplaza los datos entre corchetes con tus credenciales de Supabase (los encuentras en *Project Settings -> Database*):

```bash
pg_dump -h db.igmzzdjmzjdpwwkcrlaf.supabase.co -p 5432 -U postgres -d postgres | psql -h localhost -p 5432 -U postgres -d cruzroja