<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Lì Xì Tết 2026</title>

        <!-- Bootstrap trước -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- CSS của bạn sau -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/lixitet.css">
    </head>

    <body>

        <section class="tet-banner"></section>

        <div class="marquee">
            <div class="marquee-track">
                <img src="${pageContext.request.contextPath}/images/IMAGE2.png">
                <img src="${pageContext.request.contextPath}/images/IMAGE2.png">
            </div>
        </div>



        <!-- Section sóng -->
        <!-- Section sóng -->
        <section class="tet-wave-section">

            <div class="floating-bar">
                <img src="${pageContext.request.contextPath}/images/IMAGE3.png" alt="">
            </div>

        </section>

        <script>
            document.addEventListener("DOMContentLoaded", function () {

                const section = document.querySelector(".tet-wave-section");

                function updateSpotlight() {
                    const rect = section.getBoundingClientRect();
                    const viewportCenter = window.innerHeight / 2;

                    if (rect.top < window.innerHeight && rect.bottom > 0) {
                        const spotlightY = viewportCenter - rect.top;
                        section.style.setProperty('--spotlight-y', spotlightY + 'px');
                    }
                }

                window.addEventListener("scroll", updateSpotlight);
                updateSpotlight();
            });
        </script>
    </body>

