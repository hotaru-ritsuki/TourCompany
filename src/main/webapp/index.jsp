<%--
  Created by IntelliJ IDEA.
  User: oleksii
  Date: 11.12.2019
  Time: 12:18 дп
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <title>TourFirm</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            outline: 0;
            box-sizing: border-box;
        }
        body {
            background-image: url(https://images.unsplash.com/photo-1500021804447-2ca2eaaaabeb?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80);
            -webkit-background-size:cover;
            background-size:cover;
            background-position: center center;
            height: 100vh;
        }
        .menu-area li a {
            text-decoration: none;
        }

        .menu-area li {
            list-style-type: none;
        }

        .custom-padding{
            padding-top: 25px;
        }

        nav {
            position: relative;
            width: calc(100% - 60px);
            margin: 0 auto;
            padding: 10px 0;
            background: #333;
            z-index: 1;
            text-align: right;
            padding-right: 2%;
        }

        .logo {
            width: 15%;
            float: left;
            text-transform: uppercase;
            color: #fff;
            font-size: 25px;
            text-align: left;
            padding-left: 2%;
        }

        .menu-area li {
            display: inline-block;
        }

        .menu-area a {
            color: #fff;
            font-weight: 300;
            letter-spacing: 1px;
            text-transform: uppercase;
            display: block;
            padding: 0 25px;
            font-size: 14px;
            line-height: 30px;
            position: relative;
            z-index: 1;
        }
        .menu-area a:hover {
            background: cornflowerblue;
            color: #fff;
        }

        .menu-area a:hover:after {
            transform: translateY(-50%) rotate(-35deg);
        }

        nav:before {
            position: absolute;
            content: '';
            border-top: 10px solid #333;
            border-right: 10px solid #333;
            border-left: 10px solid transparent;
            border-bottom: 10px solid transparent;
            top: 100%;
            left: 0;
        }

        nav:after {
            position: absolute;
            content: '';
            border-top: 10px solid #333;
            border-left: 10px solid #333;
            border-right: 10px solid transparent;
            border-bottom: 10px solid transparent;
            top: 100%;
            right: 0;
        }

        /*sub menu*/
        li ul {
            position: absolute;
            min-width: 150px;
            display: none;
        }

        li > ul li {
            border: 1px solid #333333;
        }

        li > ul li a {
            padding: 10px;
            text-transform: none;
            background: #333333;
        }

        li > ul li ul {
            position: absolute;
            right: -150px;
            top: 0;
        }

        li:hover > ul {
            display: block;
        }

        .footer {
            background-color: #333333;
            color: #fff;
            display: block;
            flex: 0 0 auto;
            margin-top: 5px;
        }
         .mySlides {display:none}

        .slide-show{
            position: center;
            margin-top: 20px;
        }


    </style>
</head>
<body>
<div class="custom-padding">
    <nav>
        <div class="logo">TourFirm</div>

        <ul class="menu-area">
            <li><a href="index.html">Home</a></li>
            <li><a href="">Country</a>
                <ul class="listG">
                    <li><a href="#">German</a></li>
                    <li><a href="#">Poland</a></li>
                    <li><a href="#">Ukraine</a></li>
                </ul>
            </li>
            <li><a href="/hotel">Hotel</a></li>
            <li><a href="login">Sing in</a></li>
            <li><a href="resigtration.jsp">Sing up</a></li>
        </ul>
    </nav>
</div>
<div class="slide-show">

    <div class="w3-content" style="max-height:600px;max-width: 100%">
        <img class="mySlides" src="https://images.unsplash.com/photo-1498761987365-a2d43c363b1e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80" style="width:100%; height: 80%;">
        <img class="mySlides" src="https://images.unsplash.com/photo-1467510107305-b118251f0aca?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1953&q=80" style="width:100%; height: 80%;">
        <img class="mySlides" src="https://images.unsplash.com/reserve/2CHRCzLqSRuVRMdneTTS_unspash_003.jpg?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80" style="width:100%;height: 80%;">
    </div>

    <div class="w3-center">
        <div class="w3-section">
            <button class="w3-button w3-light-grey" onclick="plusDivs(-1)">❮ Prev</button>
            <button class="w3-button w3-light-grey" onclick="plusDivs(1)">Next ❯</button>
        </div>
    </div>

</div>

<div class="footer">
    <footer>
        Design
    </footer>
</div>
<script>
    var slideIndex = 1;
    showDivs(slideIndex);

    function plusDivs(n) {
        showDivs(slideIndex += n);
    }

    function currentDiv(n) {
        showDivs(slideIndex = n);
    }

    function showDivs(n) {
        var i;
        var x = document.getElementsByClassName("mySlides");
        var dots = document.getElementsByClassName("demo");
        if (n > x.length) {slideIndex = 1}
        if (n < 1) {slideIndex = x.length}
        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none";
        }
        for (i = 0; i < dots.length; i++) {
            dots[i].className = dots[i].className.replace(" w3-red", "");
        }
        x[slideIndex-1].style.display = "block";
        dots[slideIndex-1].className += " w3-red";
    }
</script>
</body>
</html>
