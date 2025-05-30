# API Eventos - Fuentes

## Introducción

A partir de una serie de ficheros de eventos y fuentes que los generan, los carga en memoria
de forma paralela y permite hacer busquedas sobre los eventos.

## Instrucciones desarrollo

Se ha desarrollado en netebans por lo que tiene un directorio /nbporject con los xml completos de
Ant y se puede importar y compilar desde el IDE pero tambien desde consola.

Requisitos:

- JavaJDK >21
- Ant >1.8.0

Comandos:

```bash
# compilar
ant -f build.xml compile

# compilar y lanzar (main)
ant -f build.xml run

# lanzar test
ant -f build.xml test


```
