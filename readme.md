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
# ahora mismo no hay nada que lanzar en main
ant -f build.xml run

# lanzar test
ant -f build.xml test


```

## Requisitos

Funciones:

- [x] Se cargan 1+ archivos de eventos de forma paralela
- [x] Se cargan 1+ archivos de funtes de forma paralela
- [x] Se puede buscar por fuente
- [x] Se puede buscar un intervalo de tiempo
- [x] Se puede buscar un intervalo de valores
- [x] Se imprimen las fuentes
- [ ] Se imprimen los Eventos con los datos de las fuentes

Test:
