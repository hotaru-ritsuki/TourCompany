<!DOCTYPE html>
<html>
    <head>
        <link href="./img/favicon.png" rel="shortcut icon"/>
        <meta charset="utf-8"/>
        <meta content="width=1440, maximum-scale=1.0" name="viewport"/>
        <link href="./css/loginPage.css" rel="stylesheet" type="text/css"/>
        <meta content="Launchpad by Anima" name="author">
        </meta>
    </head>
    <body style="margin: 0;
 background: rgba(255, 255, 255, 1.0);">
        <input id="anPageName" name="page" type="hidden" value="loginpage"/>
        <div class="loginpage">
            <div style="width: 1440px; height: 100%; position:relative; margin:auto;">
                <img anima-src="./img/login-page-nils-nedel-onpgbpns3cs-unsplash.png" class="nilsnedelonpgbpns3csunsplash" src="data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw=="/>
                <img anima-src="./img/login-page-rectangle-3.png" class="rectangle3" src="data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw=="/>
                <div class="buttondefaultprimary">
                    <div class="loginfacebook">
                        <div class="rectangle">
                        </div>
                        <div class="group">
                            <div class="masuk">
                                Login
                            </div>
                        </div>
                    </div>
                </div>
                <div class="buttondefaultstatefocus">
                    <div class="email">
                        <div class="rectangle">
                        </div>
                        <div class="group">
                            <div class="pratamaiosigm">
                                pratamaiosi@gm
                            </div>
                        </div>
                    </div>
                </div>
                <div class="inputdefault">
                    <div class="email">
                        <div class="rectangle">
                        </div>
                        <div class="group">
                            <div class="password">
                                Password
                            </div>
                        </div>
                    </div>
                    <div class="removeredeyematerial">
                    </div>
                </div>
                <div class="group2">
                    <div class="rectangle2">
                    </div>
                    <div class="keepmeloggedin">
                        Keep me logged in
                    </div>
                </div>
                <div class="belumpunyaakun">
                    <div class="background">
                    </div>
                    <div class="needaskripinacco">
                        <span class="span1">Need a skrip.in account? </span><span class="span2">create an account</span>
                    </div>
                </div>
                <div class="copysubtitle">
                    <div class="background">
                    </div>
                    <div class="copywritingmadesim">
                        <span class="span1">Copywriting made simpl</span><span class="span2">e</span>
                    </div>
                </div>
                <img anima-src="./img/login-page-bitmap.png" class="bitmap" src="data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw=="/>
            </div>
        </div>
        <!-- Scripts -->
        <script>
            anima_isHidden = function(e) {
                if (!(e instanceof HTMLElement)) return !1;
                if (getComputedStyle(e).display == "none") return !0; else if (e.parentNode && anima_isHidden(e.parentNode)) return !0;
                return !1;
            };
            anima_loadAsyncSrcForTag = function(tag) {
                var elements = document.getElementsByTagName(tag);
                var toLoad = [];
                for (var i = 0; i < elements.length; i++) {
                    var e = elements[i];
                    var src = e.getAttribute("src");
                    var loaded = (src != undefined && src.length > 0 && src != 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==');
                    if (loaded) continue;
                    var asyncSrc = e.getAttribute("anima-src");
                    if (asyncSrc == undefined || asyncSrc.length == 0) continue;
                    if (anima_isHidden(e)) continue;
                    toLoad.push(e);
                }
                toLoad.sort(function(a, b) {
                    return anima_getTop(a) - anima_getTop(b);
                });
                for (var i = 0; i < toLoad.length; i++) {
                    var e = toLoad[i];
                    var asyncSrc = e.getAttribute("anima-src");
                    e.setAttribute("src", asyncSrc);
                }
            };
            anima_pauseHiddenVideos = function(tag) {
                var elements = document.getElementsByTagName("video");
                for (var i = 0; i < elements.length; i++) {
                    var e = elements[i];
                    var isPlaying = !!(e.currentTime > 0 && !e.paused && !e.ended && e.readyState > 2);
                    var isHidden = anima_isHidden(e);
                    if (!isPlaying && !isHidden && e.getAttribute("autoplay") == "autoplay") {
                        e.play();
                    } else if (isPlaying && isHidden) {
                        e.pause();
                    }
                }
            };
            anima_loadAsyncSrc = function(tag) {
                anima_loadAsyncSrcForTag("img");
                anima_loadAsyncSrcForTag("iframe");
                anima_loadAsyncSrcForTag("video");
                anima_pauseHiddenVideos();
            };
            var anima_getTop = function(e) {
                var top = 0;
                do {
                    top += e.offsetTop || 0;
                    e = e.offsetParent;
                } while (e);
                return top;
            };
            anima_loadAsyncSrc();
            anima_old_onResize = window.onresize;
            anima_new_onResize = undefined;
            anima_updateOnResize = function() {
                if (anima_new_onResize == undefined || window.onresize != anima_new_onResize) {
                    anima_new_onResize = function(x) {
                        if (anima_old_onResize != undefined) anima_old_onResize(x);
                        anima_loadAsyncSrc();
                    };
                    window.onresize = anima_new_onResize;
                    setTimeout(function() {
                        anima_updateOnResize();
                    }, 3000);
                }
            };
            anima_updateOnResize();
            setTimeout(function() {
                anima_loadAsyncSrc();
            }, 200);
        </script>
        <!-- End of Scripts -->
    </body>
</html>
