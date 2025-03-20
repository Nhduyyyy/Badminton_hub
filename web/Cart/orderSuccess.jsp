<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Đặt hàng thành công</title>
        <link rel="stylesheet" href="styles.css"> 
        <jsp:include page="/Common/header.jsp"></jsp:include>
            <style>
                /* Reset một số kiểu mặc định */
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }

                /* Đặt nền cho trang */
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background: linear-gradient(135deg, #f8f9fa, #e9ecef);
                    color: #343a40;
                    min-height: 100vh;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }

                /* Trung tâm nội dung */
                center {
                    background: #fff;
                    border-radius: 8px;
                    padding: 40px 60px;
                    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                    text-align: center;
                }

                /* Style cho tiêu đề */
                h2 {
                    font-size: 2.5rem;
                    margin-bottom: 20px;
                    color: #28a745;
                }

                /* Style cho đoạn text */
                p {
                    font-size: 1.1rem;
                    margin-bottom: 30px;
                }

                /* Kiểu cho các nút */
                a button {
                    background: #007bff;
                    border: none;
                    border-radius: 4px;
                    color: #fff;
                    padding: 12px 25px;
                    margin: 0 10px;
                    font-size: 1rem;
                    cursor: pointer;
                    transition: background 0.3s ease, transform 0.3s ease;
                }

                /* Hiệu ứng hover cho nút */
                a button:hover {
                    background: #0056b3;
                    transform: scale(1.05);
                }

                /* Responsive: giảm padding khi màn hình nhỏ */
                @media (max-width: 768px) {
                    center {
                        padding: 30px 20px;
                    }
                    h2 {
                        font-size: 2rem;
                    }
                    a button {
                        padding: 10px 20px;
                        margin: 10px 0;
                    }
                }
            </style>
        </head>
        <body>
        <center>
            <h2>🎉 Đặt hàng thành công! 🎉</h2>
            <p>Cảm ơn bạn đã mua hàng. Chúng tôi sẽ sớm xử lý đơn hàng của bạn.</p>

            <a href="/badminton/home.jsp">
                <button>Về trang chủ</button>
            </a>
            <a href="/badminton/Product/productList.jsp">
                <button>Tiếp tục mua sắm</button>
            </a>
        </center>

    <jsp:include page="/Common/footer.jsp"/>
</body>
</html>