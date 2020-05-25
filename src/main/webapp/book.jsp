<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="bookComparator.Book"%>
<%@ page import ="bookComparator.BookOffer"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Resultados búsqueda</title>
    <link rel="stylesheet" href="https://necolas.github.io/normalize.css/8.0.1/normalize.css">
    <link href="https://fonts.googleapis.com/css?family=Staatliches" rel="stylesheet">
    <link rel="stylesheet" href="css/style_book.css">
    
</head>
<body>
    <header class="header">
        <h1>Books<span>Comparator</span></h1>
    </header>

    <div class="barra">
        <nav class="nav">
            <a href="index.html">Inicio</a>
        </nav>
    </div>

    <main>
        <h2>Resultados de la búsqueda</h2>

        <div class="cabecera contenedor">
            <img src="${book.image}" alt="logo">
            <div class="descripcion">
                <p><span>Titulo: </span>${book.titulo}</p>
                <p><span>Autor: </span> ${book.autores}</p>
                <p><span>Editorial: </span>${book.editorial}</p>
                <p><span>ISBN: </span> ${book.isbn}</p>
            </div>
        </div>

        <div class="lista-productos contenedor">
            <div class="producto">
                <a href="${book.ofertas[0].url}">
                    <img src="img/amazon_logo.png" alt="" srcset="">
                </a>
                <div class="contenido-producto">
                    <p><div class="precio">${book.ofertas[0].precio}</div></p>
                </div>
            </div>

            <div class="producto">
                <a href="${book.ofertas[1].url}">
                    <img src="img/popular_logo.jpg" alt="" srcset="">
                </a>
                <div class="contenido-producto">
                    <p><div class="precio">${book.ofertas[1].precio}</div></p>
                </div>
            </div>

            <div class="producto">
                <a href="${book.ofertas[2].url}">
                    <img src="img/agapea_logo.png" alt="" srcset="">
                </a>
                <div class="contenido-producto">
                    <p><div class="precio">${book.ofertas[2].precio}</div></p>
                </div>
            </div>
        </div>
    </main>
</body>
</html>