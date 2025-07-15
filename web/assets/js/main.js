/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

//   Theme
const body = document.querySelector('body');
function toggleTheme(type) {

    const header = document.querySelector('header');
    if (type === 'Dark') {
        body.classList.add('toggle-body');
    } else {
        body.classList.remove('toggle-body');
    }
    localStorage.setItem('theme', type);
}

//   Language

function googleTranslateElementInit() {
    new google.translate.TranslateElement({pageLanguage: 'en', includedLanguages: 'en,vi'}, 'google_translate_element');
}

function changeLanguage(lang) {
    let select = document.querySelector(".goog-te-combo");
    if (select) {
        if (select.value === lang) {
            return;
        }
        select.value = lang;
        select.dispatchEvent(new Event('change'));

    } else {
        console.error("Google Translate dropdown not found!");
    }
}


function customTranslateVN() {
    let translationsVN = {
        "Bakery": "Bánh ngọt"
    };
    document.querySelectorAll("body *[data-language]").forEach((tag) => {
        let key = tag.getAttribute("data-language");
        if (translationsVN[key]) {
            tag.textContent = translationsVN[key];
        }
    });
}

function customTranslateEN() {
    let translationsEN = {
        "Bakery": "Bakery"
    };
    document.querySelectorAll("body *[data-language]").forEach((tag) => {
        let key = tag.getAttribute("data-language");
        if (translationsEN[key]) {
            tag.textContent = translationsEN[key];
        }
    });
}


//   Load theme and languege

window.onload = function () {
    const savedTheme = localStorage.getItem('theme'); // Get value from LocalStorage
    if (savedTheme === 'Dark') {
        toggleTheme('Dark');
    }

    const savedLanguage = localStorage.getItem('language'); // Get value from LocalStorage
    if (savedLanguage === 'vn') {
        changeLanguage('vn');
    }

    body.classList.add('loaded');
};

//   Hander header section
let headerNavs = document.querySelectorAll('.header-navbar ul li');
if (headerNavs) {
    let line = document.querySelector('.line');
    Array.from(headerNavs).forEach((li) => {
        li.onclick = function () {
            line.style.left = this.offsetLeft / 10 + "rem";
            line.style.width = this.offsetWidth / 10 + "rem";
        };
    });
}

// Product carousel

let currentIndexProduct = 0;
let visibleProduct = 4;
let productSize = document.querySelector('.new-products-block[product-size]');
(productSize !== null) ? productSize = productSize.getAttribute('product-size') : null;
const productBlock = document.querySelector('.new-products-block');
let itemProducts;
(productBlock !== null) ? itemProducts = Array.from(productBlock.children) : null;

function updateCarousel() {

    let itemWidth;
    if (itemProducts) {
        itemWidth = itemProducts[0].offsetWidth;
        productBlock.style.transform = `translateX(-${currentIndexProduct * itemWidth}px)`;
        productBlock.style.transition = 'transform linear .2s';
        const productLeft = document.querySelector('.new-products-body-left');
        if (productLeft)
            productLeft.classList.toggle('active', currentIndexProduct === 0);
        const productRight = document.querySelector('.new-products-body-right');
        if (productRight)
            productRight.classList.toggle('active', currentIndexProduct === productSize - visibleProduct);
        const indicatorItem = document.querySelector('.new-product-indicators .active');
        let indicatorItems = document.querySelectorAll('.new-product-indicators div');
        if (indicatorItem) {
            indicatorItem.classList.remove('active');
            indicatorItems[currentIndexProduct].classList.add('active');
        }
    }

}

function carouselProduct(action = 'next') {
    switch (action) {
        case 'next':
            currentIndexProduct += 1;
            break;
        default:
            currentIndexProduct -= 1;
    }

    updateCarousel();
}
window.addEventListener('resize', () => {
    const indicator = document.querySelector('.new-product-indicators');
    if (indicator === null)
        return;
    if (window.innerWidth < 992) {
        visibleProduct = 2;
    } else {
        visibleProduct = 4;
    }
    if (productSize > visibleProduct) {
        let html = '';
        for (let i = visibleProduct; i <= productSize; i++) {
            if (i === visibleProduct) {
                html += `<div onclick='updateIndex(${i - visibleProduct})' class='active'></div>`;
            } else {
                html += `<div onclick='updateIndex(${i - visibleProduct})'></div>`;
            }
        }
        indicator.innerHTML = html;
    }
    currentIndexProduct = 0;
    updateCarousel();
}
);

function updateIndex(index) {
    currentIndexProduct = Number(index);
    updateCarousel();
}


function seacrh(contain) {
    contain.style.width = '80%';
    const input = contain.querySelector('.filter-search-input');
    input.focus();
}


//      Soan  -  News

function getNews(id = 0) {
    const side_bar = document.querySelector(`.side-bar`);
    if (side_bar && window.getComputedStyle(side_bar).display === 'flex') {
        handleNavBar(`none`);
    }
    window.scroll({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });
    const main = document.querySelector('#main');
    const url = `news?id=${id}`;
    fetch(url)
            .then(response => response.text())
            .then(data => {
                if (data === 'error') {
                    alert(`News is'nt exists!`);
                } else {
                    main.innerHTML = data;
                }
            })
            .catch(error => console.log(error));
}

//      Soan  -  Store

function getStore() {
    const side_bar = document.querySelector(`.side-bar`);
    if (side_bar && window.getComputedStyle(side_bar).display === 'flex') {
        handleNavBar(`none`);
    }
    window.scroll({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });
    const main = document.querySelector('#main');
    const url = `store?action=list`;
    fetch(url)
            .then(response => response.text())
            .then(data => {
                main.innerHTML = data;
            })
            .catch(error => console.log(error));
}

//      Login - Register - Forgot password

const acc_header = document.querySelector('.account-header');
const acc_body = document.querySelector('.account-body');

let error = document.querySelector('.error');
let render_error = document.querySelector('.render-error');

function resetSpan(span) {
    span.style.top = '-40%';
    span.style.padding = '0 0.5rem';
    span.style.color = 'var(--active-btn)';
    span.style.fontSize = '1.3rem';
    (localStorage.getItem('theme') === null || localStorage.getItem('theme') === 'Light') ? span.style.backgroundColor = '#fff' : "";
    span.style.zIndex = '2';
}

function resetInputsEvents() {
    const inputElements = document.querySelectorAll('.form-input input');
    Array.from(inputElements).forEach((input) => {
        const parent = input.parentElement;
        const message = parent.querySelector('.message');
        const span = parent.querySelector('span');
        if (input.value) {
            resetSpan(span);
        }

        input.onclick = () => {
            resetSpan(span);
            parent.style.border = '0.2rem solid var(--active-click)';
        };

        input.oninput = () => {
            console.log("1");
            resetSpan(span);
            resetMessage();
            if (message)
                message.textContent = '';
        };

        input.onblur = () => {
            if (!input.value) {
                span.style.top = '20%';
                span.style.padding = '0';
                span.style.color = '#999';
                span.style.fontSize = '1.5rem';
                span.style.zIndex = '0';
            }
            parent.style.border = '0.1rem solid #999';
        };
    });
}
resetInputsEvents();

function handelAccount(action, email = '', data = '') {
    let title = document.querySelector('.title');
    if (title !== null) {
        if ((action === 'login' && title.textContent === 'Login') ||
                (action === 'register' && title.textContent === 'Register')) {
            return; // No action needed
        }
    }

    acc_header.style.animation = 'none';
    acc_body.style.animation = 'none';
    setTimeout(() => {
        acc_header.style.animation = 'toRight ease-in-out .5s';
        acc_body.style.animation = 'toRight ease-in-out .5s';
    }, 10);

    let url;
    resetMessage();
    switch (action) {
        case 'login':
            window.location.href = 'login';
//            const acc_body_login = acc_body.innerHTML;
//            acc_body.innerHTML = acc_body_login;
//            resetMessage();
//            resetInputsEvents();
//            history.pushState(null, "", "login");
            break;
        case 'register':

            url = '/lighthouse/register';
            fetch(url + "?method=get-data")
                    .then(response => response.text())
                    .then(data => {
                        acc_body.innerHTML = data;
                        Validation({
                            form: '.form-group',
                            url: 'register',
                            rules: [
                                Validation.isRequire('input[name=fullname]'),
                                Validation.isValidName('input[name=username]'),
                                Validation.isValidEmail('input[name=email]'),
                                Validation.isValidPhone('input[name=phone]'),
                                Validation.isValidPassword('input[name=password]', 1),
                                Validation.confirmPassword('input[name=confirm-password]', () => {
                                    return document.querySelector('input[name=password').value;
                                })
                            ]
                        });
                        resetInputsEvents();
                    })
                    .catch(err => console.error("Error: " + err));
            history.pushState(null, "", url);
            break;
        case 'forgot-password':
            url = '/lighthouse/forgot-password';
            fetch(url + "?method=get-data")
                    .then(response => response.text())
                    .then(data => {
                        acc_body.innerHTML = data;
                        Validation({
                            form: '.form-group',
                            url: 'forgot-password',
                            rules: [
                                Validation.isValidEmail('input[name=email]')
                            ]
                        });
                        resetInputsEvents();
                    })
                    .catch(err => console.error("Error: " + err));
            history.pushState(null, "", url);
            break;
        case 'verify-email':
            acc_body.innerHTML = (email !== null) ? data :
                    `There's something was wrong`;
            discruptSpan();
            // history.pushState(null, "", 'verify-email');
}
}

function discruptSpan() {
    const span = document.querySelector('.resent-pass');
    // console.log(span);
    if (span) {
        let timeLeft = 30;
        span.classList.add('discrupt');
        let countdown = setInterval(function () {
            timeLeft--;
            span.innerText = `Resent code (${timeLeft})`;

            if (timeLeft <= 0) {
                clearInterval(countdown);
                span.classList.remove('discrupt');
                span.innerText = "Resent code";
            }
        }, 1000); // Cập nhật mỗi giây
    }
}

function resetMessage() {
    if (render_error)
        render_error.textContent = '';
    if (error) {
        error.textContent = '';
    }
}

function displayPass(span) {
    if (span) {
        const input = span.parentElement.querySelector('input');
        const icon = span.querySelector('.eye-icon');
        if (input.type === 'password') {
            input.type = 'text';
            if (icon.classList.contains('fa-eye-slash')) {
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        } else {
            input.type = 'password';
            if (icon.classList.contains('fa-eye')) {
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            }
        }

    }
}

function moveFocus(index) {
    render_error.textContent = '';
    if (document.getElementById(`code${index}`).value.length === 1) {
        if (index < 6) {
            document.getElementById(`code${index + 1}`).focus();
            // console.log(index);
        }
    } else {
        if (index > 1) {
            document.getElementById(`code${index - 1}`).focus();
            // console.log(index);
            // console.log(document.getElementById(`code${index + 1}`));
        }
    }
}

function verifyCode(email) {
    let enteredCode = "";
    for (let i = 1; i <= 6; i++) {
        enteredCode += document.getElementById(`code${i}`).value;
    }
    fetch('/lighthouse/register?method=code&enteredCode=' + enteredCode + '&email=' + email, {method: 'post'})
            .then(response => response.text())
            .then(data => {
                render_error.style.animation = 'none';
                setTimeout(() => {
                    render_error.style.animation = 'toRight linear .3s';
                }, 10);
                switch (data) {
                    case "success":
                        render_error.style.color = 'green';
                        render_error.textContent = "The code is correct! You have successfully verified.";
                        setTimeout(() => {
                            window.location.href = 'home';
                        }, 3000);
                        break;
                    case "invalid":
                        render_error.textContent = 'The code is invalid. Please try again!';
                        break;
                    case "expired":
                        render_error.textContent = 'The code is expired. Please try again!';
                        break;
                }
            })
            .catch(err => console.error("Error: " + err));
    // history.pushState(null, "", 'verify-email');

}

function verifyCodeToResetPassword(email) {
    let enteredCode = "";
    for (let i = 1; i <= 6; i++) {
        enteredCode += document.getElementById(`code${i}`).value;
    }
    fetch('/lighthouse/forgot-password?method=code&enteredCode=' + enteredCode + '&email=' + email, {method: 'post'})
            .then(response => response.text())
            .then(data => {
                render_error.style.animation = 'none';
                setTimeout(() => {
                    render_error.style.animation = 'toRight linear .3s';
                }, 10);
                if (data === 'success') {
                    render_error.style.color = 'green';
                    render_error.textContent = "The code is correct! A new password is send to your email.";
                    setTimeout(() => {
                        window.location.href = 'home';
                    }, 5000);
                } else {
                    render_error.textContent = data;
                }

                // resetInputsEvents();
            })
            .catch(err => console.error("Error: " + err));
    // history.pushState(null, "", 'verify-email');

}

function resentCode(email, token) {
    fetch('/lighthouse/register?method=resent-code&email=' + email + '&token=' + token, {method: 'post'})
            .then(response => response.text())
            .then(data => {

                if (data === 'exceeding') {
                    render_error.style.animation = 'none';
                    setTimeout(() => {
                        render_error.style.animation = 'toRight linear .3s';
                    }, 10);
                    render_error.style.marginTop = '2rem';
                    render_error.textContent = 'Your number of requests has exceeded the daily limit!';
                } else {
                    acc_body.innerHTML = data;
                    render_error.style.animation = 'none';
                    setTimeout(() => {
                        render_error.style.animation = 'toRight linear .3s';
                    }, 10);
                    render_error.style.marginTop = '2rem';
                    render_error.textContent = 'A new verification code has been sent to your email.';
                    // resetInputsEvents();
                }
                discruptSpan();
            })
            .catch(err => console.error("Error: " + err));
    // history.pushState(null, "", 'verify-email');
}

function resentCodeToResetPassword(email) {
    fetch('/lighthouse/forgot-password?method=resent-code&email=' + email, {method: 'post'})
            .then(response => response.text())
            .then(data => {

                if (data === 'exceeding') {
                    render_error.style.animation = 'none';
                    setTimeout(() => {
                        render_error.style.animation = 'toRight linear .3s';
                    }, 10);
                    render_error.style.marginTop = '2rem';
                    render_error.textContent = 'Your number of requests has exceeded the daily limit!';
                } else {
                    acc_body.innerHTML = data;
                    render_error.style.animation = 'none';
                    setTimeout(() => {
                        render_error.style.animation = 'toRight linear .3s';
                    }, 10);
                    render_error.style.marginTop = '2rem';
                    render_error.textContent = 'A new verification code has been sent to your email.';
                    // resetInputsEvents();
                }
                discruptSpan();
            })
            .catch(err => console.error("Error: " + err));
    // history.pushState(null, "", 'verify-email');
}


window.addEventListener("popstate", (e) => {
    const currentUrl = window.location.href;
    (currentUrl.endsWith("login")) ? handelAccount("login") :
            (currentUrl.endsWith("register")) ? handelAccount("register") :
            (currentUrl.endsWith("forgot-password")) ? handelAccount("forgot-password") :
            (currentUrl.endsWith("verify-email")) ? handelAccount("verify-email") :
            '';
});



//       Category - Product detail

function handleNavBar(action) {
    const nav_header = document.querySelector('.header-navbar');
    const modal = document.querySelector('.modall');
    nav_header.style.display = `${action}`;
    modal.style.display = `${action}`;

}

function collection(id) {
    const side_bar = document.querySelector(`.side-bar`);
    if (side_bar && window.getComputedStyle(side_bar).display === 'flex') {
        handleNavBar(`none`);
    }

    const main = document.querySelector('#main');
    const url = "category?id=" + id;
    fetch(url)
            .then(response => response.text())
            .then(data => main.innerHTML = data)
            .catch(error => console.log(error));
    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });
}

function paging(currPage, cId, page) {
    const prePage = document.querySelector('.paging-product .active');
    if (prePage)
        prePage.classList.remove('active');
    currPage.classList.add('active');

    const product_container = document.querySelector('.show-products-container');
    product_container.style.animation = 'none';
    setTimeout(function () {
        product_container.style.animation = 'toLeft linear .3s';
    }, 10);
    const url = "category?id=" + cId + "&page=" + page;
    fetch(url)
            .then(response => response.text())
            .then(data => product_container.innerHTML = data)
            .catch(error => console.log(error));

    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });

}

function doSearch(input, cId, action) {
    let value;
    let price_from_value;
    let price_to_value;
    if (action === "searchByName") {
        value = input.value;
    } else if (action === "searchByPrice") {
        const price_from = document.querySelector('input[name=price-from]');
        const price_to = document.querySelector('input[name=price-to]');
        price_from_value = price_from.value.replace(/[^0-9]/g, '');
        price_to_value = price_to.value.replace(/[^0-9]/g, '');
        if (Number(price_to_value) <= Number(price_from_value)) {
            const err_price = document.querySelector('.err-price span');
            err_price.style.animation = 'none';
            setTimeout(function () {
                err_price.style.animation = 'toRight linear .3s';
            }, 10);
            err_price.innerHTML = 'The "Price From" value must be greater than the "Price To" value!';
            return;
        } else {
            value = price_from_value + "-" + price_to_value;
        }
    } else if (action === "searchByStar") {
        value = input;
    } else if (action === "searchByDiscount") {
        const preInput = document.querySelector(".filter-discount-item input:checked");
        const currInput = input.querySelector("input");
        if (preInput)
            preInput.checked = false;
        currInput.checked = true;
        value = currInput.value;
        window.scrollTo({
            top: 0,
            left: 0,
            behavior: 'smooth'
        });
    }

    const url = "category?id=" + cId + "&action=" + action + "&value=" + value;
    const product_container = document.querySelector('.show-products-container');
    product_container.style.animation = 'none';
    setTimeout(function () {
        product_container.style.animation = 'toLeft linear .3s';
    }, 10);
    fetch(url, {method: 'post'})
            .then(response => response.text())
            .then(data => product_container.innerHTML = data)
            .catch(error => console.log(error));
}

function deteleVal(input) {
    input.value = '';
}

function formatCurrencyVND(input) {
    const err_price = document.querySelector('.err-price span');
    if (err_price)
        err_price.innerHTML = "";
    let value = input.value;
    value = value.replace(/[^0-9]/g, '');
    value = Number(value).toLocaleString('vi-VN');
    input.value = value;
}

function productDetail(id) {
    const main = document.querySelector('#main');
    const url = "product?id=" + id;
    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });
    fetch(url)
            .then(response => response.text())
            .then(data => main.innerHTML = data)
            .catch(err => console.log(err));
}

function changeImg(img) {
    const mainImg = document.querySelector('.product-detail-img-main img');
    const currImg = document.querySelector('.thumbImgs img.active');
    if (mainImg.src === img.src)
        return;
    mainImg.style.animation = 'none';
    setTimeout(function () {
        mainImg.style.animation = 'toBotton ease-in .3s';
    }, 10);
    if (currImg)
        currImg.classList.remove('active');
    img.classList.add('active');
    mainImg.src = img.src;
}

function chooseItem(li, action) {
    const product_price = document.querySelector('.product-price');
    const quantity_value = document.querySelector('.product-quantity span').textContent;
    let currPrice = product_price.textContent.replace(/[^0-9]/g, '');
    let prePrice;
    let preLi;
    let preSvg;
    if (action === 'size') {
        preLi = document.querySelector('.product-size li.active');
        if (preLi) {
            preLi.classList.remove('active');
            preSvg = preLi.querySelector('svg');
            preSvg.style.fill = '#000';
            prePrice = preLi.querySelector('.product-size-desc');
            currPrice = Number(currPrice) - Number(prePrice.textContent.replace(/[^0-9]/g, '')) * Number(quantity_value);
        }
        li.querySelector('svg').style.fill = '#fff';
        li.classList.add('active');
        currPrice = Number(currPrice) + Number(li.textContent.replace(/[^0-9]/g, '')) * Number(quantity_value);
    } else {
        li.classList.toggle('active');
        if (li.classList.contains('active')) {
            currPrice = Number(currPrice) + Number(li.textContent.replace(/[^0-9]/g, '')) * Number(quantity_value);
        } else {
            currPrice = Number(currPrice) - Number(li.textContent.replace(/[^0-9]/g, '')) * Number(quantity_value);
        }
    }

    product_price.textContent = Number(currPrice).toLocaleString('vi-VN') + "đ";
}

function changeQuantity(action, btn = null) {
    const span = document.querySelector('.product-quantity span');
    const desc_btn = document.querySelector('.product-quantity .disable');
    const product_price = document.querySelector('.product-price');
    let price = Number(product_price.textContent.replace(/[^0-9]/g, ''));
    let spanValue = Number(span.textContent);
    const initPrice = price / spanValue;
    if (action === 'insc') {
        spanValue += 1;
        if (desc_btn) {
            desc_btn.classList.remove('disable');
        }
        price += initPrice;
    } else {
        spanValue -= 1;
        if (spanValue === 1)
            btn.classList.add('disable');
        price -= initPrice;
    }
    span.textContent = spanValue;
    product_price.textContent = Number(price).toLocaleString('vi-VN') + "đ";
}

function adToCart(btn, src, pId) {
    const quantityItemCart = document.querySelector('.header-right-cart-quantity span');

    if (quantityItemCart.innerHTML === '0') {
        const header_right_cart_info = document.querySelector('.header-right-cart-info');
        header_right_cart_info.innerHTML = `<div class="header-right-cart-info-header">
                                                                                    Your Cart
                                                                                </div>
                                                                                <div class="header-right-cart-info-items"></div>
                                                                                <div class="cart-info-total-product"></div>`;
        header_right_cart_info.style.padding = '0';
    }

    // Create an Order
    const totalPrice = document.querySelector('.product-price').textContent.replace(/[^0-9]/g, '');
    const quantity = document.querySelector('.product-quantity span').textContent;

    const product_size = document.querySelector('.product-size li.active .product-size-desc');
    let size = "";
    if (product_size) {
        const product_size_value = product_size.textContent;
        const product_size_type = product_size_value.charAt(0);
        const product_size_price = Number(product_size_value.replace(/[^0-9]/g, '')) / 1000;
        size = product_size_type + "-" + product_size_price;
    }

    let topping = "";
    const product_toppings = document.querySelectorAll('.product-topping li.active');
    if (product_toppings) {
        Array.from(product_toppings).forEach(function (li) {
            const product_topping_desc = li.querySelector('.product-topping-desc').textContent;
            const indexPlus = product_topping_desc.indexOf("+");
            const product_topping_type = product_topping_desc.substring(0, indexPlus).trim();
            const product_topping_price = Number(product_topping_desc.replace(/[^0-9]/g, '')) / 1000;
            topping += product_topping_type + "-" + product_topping_price + ";";
        });
        topping = topping.substring(0, topping.length - 1);
    }

    const payment = document.querySelector('.product-payment').value;
    const url = `order?&pId=${pId}&action=cart&price=${totalPrice}&quantity=${quantity}&size=${size}&topping=${topping}&payment=${payment}`;

    // console.log(url);
    const cartItems = document.querySelector('.header-right-cart-info-items');
    fetch(url, {
        method: 'POST',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }})
            .then(response => {
                if (response.status === 401) {
                    // Nếu nhận HTTP 401 (chưa đăng nhập), chuyển hướng về login
                    window.location.href = "login";
                    throw new Error("User not logged in");
                }
                return response.text();
            })
            .then(data => {
                cartItems.insertAdjacentHTML("beforeend", data);
                doCartAnimation(btn, src, quantityItemCart);
            })
            .catch(err => console.log(err));

}

function doCartAnimation(btn, src, quantityItemCart) {
    const parent = btn.parentElement;
    const cart = document.querySelector('.header-right-cart');

    const cartAnimation = document.createElement("div");
    cartAnimation.classList.add("cart-animation");

    const img = document.createElement("img");
    img.src = src;
    img.alt = "alt";

    cartAnimation.appendChild(img);

    parent.appendChild(cartAnimation);

    const btnRect = btn.getBoundingClientRect();
    const cartRect = cart.getBoundingClientRect();

    const startX = btnRect.left + btnRect.width / 2;
    const startY = btnRect.top + btnRect.height / 2;
    const endX = cartRect.left + cartRect.width / 2;
    const endY = cartRect.top + cartRect.height / 2;


    const deltaX = endX - startX;
    const deltaY = endY - startY;
    // cartAnimation.style.visibility = 'visible';

    setTimeout(() => {
        cartAnimation.style.transform = `translate(${deltaX}px, ${deltaY}px) scale(1)`;
    }, 100);

    setTimeout(() => {
        cartAnimation.style.transform = `translate(${deltaX}px, ${deltaY}px) scale(0)`;
    }, 1500);

    quantityItemCart.innerHTML = Number(quantityItemCart.innerHTML) + 1;
    const totalItems = document.querySelector('.cart-info-total-product');
    if (quantityItemCart.innerHTML <= 1) {
        totalItems.innerHTML = "Total " + quantityItemCart.innerHTML + " product";
    } else {
        totalItems.innerHTML = "Total " + quantityItemCart.innerHTML + " products";
    }
}

function removeItemCart(oId, btn, e) {
    e.stopPropagation();
    const url = `order?id=${oId}&action=delete-order`;
    fetch(url)
            .then(response => response.text())
            .then(data => {
                if (data === 'Detele Successfullly') {
                    const quantityItemCart = document.querySelector('.header-right-cart-quantity span');

                    quantityItemCart.innerHTML = Number(quantityItemCart.innerHTML) - 1;
                    if (quantityItemCart.innerHTML === '0') {
                        const header_right_cart_info = document.querySelector('.header-right-cart-info');
                        header_right_cart_info.innerHTML = `<img src="./assets/imgs/cart-empty.png" alt="cart-empty image"/>
                                                                                    <h3>Your cart is empty</h3>
                                                                                    <p>Looks like you haven't made</p>
                                                                                    <p>Your choice yet...</p>`;
                        header_right_cart_info.style.padding = '1rem 0';
                        return;
                    } else {
                        const totalItems = document.querySelector('.cart-info-total-product');
                        if (quantityItemCart.innerHTML <= 1) {
                            totalItems.innerHTML = "Total " + quantityItemCart.innerHTML + " product";
                        } else {
                            totalItems.innerHTML = "Total " + quantityItemCart.innerHTML + " products";
                        }
                    }

                    const cartItem = btn.closest('.header-right-cart-info-item');

                    if (cartItem) {
                        cartItem.remove(); // Xóa phần tử khỏi DOM
                    }

                } else {
                    console.log(data);
                }
            })
            .catch(err => console.log(err));
}

function buyProduct(pId) {
    const totalPrice = document.querySelector('.product-price').textContent.replace(/[^0-9]/g, '');
    const quantity = document.querySelector('.product-quantity span').textContent;

    const product_size = document.querySelector('.product-size li.active .product-size-desc');
    let size = "";
    if (product_size) {
        const product_size_value = product_size.textContent;
        const product_size_type = product_size_value.charAt(0);
        const product_size_price = Number(product_size_value.replace(/[^0-9]/g, '')) / 1000;
        size = product_size_type + "-" + product_size_price;
    }

    let topping = "";
    const product_toppings = document.querySelectorAll('.product-topping li.active');
    if (product_toppings) {
        Array.from(product_toppings).forEach(function (li) {
            const product_topping_desc = li.querySelector('.product-topping-desc').textContent;
            const indexPlus = product_topping_desc.indexOf("+");
            const product_topping_type = product_topping_desc.substring(0, indexPlus).trim();
            const product_topping_price = Number(product_topping_desc.replace(/[^0-9]/g, '')) / 1000;
            topping += product_topping_type + "-" + product_topping_price + ";";
        });
        topping = topping.substring(0, topping.length - 1);
    }

    const payment = document.querySelector('.product-payment').value;
    const url = `order?&pId=${pId}&action=buy&desc=buyProduct&price=${totalPrice}&quantity=${quantity}&size=${size}&topping=${topping}&payment=${payment}`;

    const cartItems = document.querySelector('.header-right-cart-info-items');
    fetch(url, {
        method: 'post',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }})
            .then(response => {
                if (response.status === 401) {
                    // Nếu nhận HTTP 401 (chưa đăng nhập), chuyển hướng về login
                    window.location.href = "login";
                    throw new Error("User not logged in");
                }
                return response.text();
            })
            .then(data => {
                if (data === 'address-require' || data === 'error-data' 
                || data === 'insufficient-balance' || data === 'insufficient-quantity') {
                    const box = document.querySelector(`.${data}`);
                    box.style.display = 'flex';
                } else
                    // Redirect to sanbox or order
                    window.location.href = data;
            })
            .catch(err => console.log(err));
}

//           Profie

const inputs = document.querySelectorAll('.profile-form-group input');

const status_btn_profile = document.querySelector('.profile-right-body-submit span');
if (inputs) {
    Array.from(inputs).forEach(input => {
        if (input.name !== 'email') {
            input.oninput = function () {
                render_error.textContent = '';
            };
        }
    });
}
function editProfile(btn, e) {

    const profile_form = document.querySelector('.profile-right-body');
    if (profile_form) {
        if (status_btn_profile) {
            if (status_btn_profile.textContent === 'Edit') {
                e.preventDefault();
                status_btn_profile.textContent = 'Save';
                Array.from(inputs).forEach(input => {
                    if (input.name !== 'email') {
                        input.classList.toggle('active');
                    }
                });
            } else {
                Validation({
                    form: '.profile-right-body',
                    url: 'profile',
                    rules: [
                        Validation.isRequire('input[name=fullname]'),
                        Validation.isValidName('input[name=username]'),
                        // Validation.isValidEmail('input[name=email]'),
                        Validation.isValidPhone('input[name=phone]'),
                        Validation.isValidPassword('input[name=password]', 1)
                    ]
                });

            }
        }

    }
}

function changeAvatar(div) {
    const input = div.querySelector('#fileInput');
    if (input) {
        input.click();
    }
}

function doChangeAvatar(input, e) {
    const file = e.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const img = document.querySelector('.profile-left-body-avatar-img');
            const avatar = document.querySelector('.avatar');
            img.src = e.target.result;
            avatar.src = e.target.result;
        };
        reader.readAsDataURL(file); // Đọc file dưới dạng URL

        // Gửi file lên server bằng AJAX
        const formData = new FormData();
        formData.append("image", file);

        fetch("profile?method=updateImage", {
            method: "POST",
            body: formData
        })
                .then(response => response.text())
                .then(data => console.log(data))
                .catch(error => console.error("Error:", error));
    }
}

function reCharge() {
    const input = document.querySelector('.profile-left-input');
    if (input) {
        setTimeout(() => {
            input.style.opacity = '1';
            input.style.transform = 'translateY(-0.3rem)';
        }, 10);
        input.style.display = 'flex';
    }
}

function doRecharge(btn) {
    const parent = btn.parentElement;
    if (parent) {
        const input = parent.querySelector('input');
        if (input) {
            const value = input.value.replace(/[.]/g, '');
            console.log(value);
            if (value < 10000) {
                const err_balance = parent.querySelector('.err-balance span');
                err_balance.textContent = `At least 10.000 VND`;
                err_balance.style.animation = 'none';
                setTimeout(() => {
                    err_balance.style.animation = 'toRight linear .2s';
                }, 10);
            } else {
                const url = `order?action=buy&desc=recharge&price=${value}`;
                fetch(url, {
                    method: 'post'
                })
                        .then(response => response.text())
                        .then(data => window.location.href = data)
                        .then(err => console.log(err));
            }
        }
    }
}

//     Handel  Box
const box_modal = document.querySelector('.box-modal');

function closeBox(action, typeBox = '') {

    const supplier_delete = document.querySelector('.supplier-delete-id');
    const box_modal_delete = document.querySelector('.delete-supplier-sccess');
    const box = document.querySelector('.box-modal');
    box.style.display = 'none';
    let order_cancel, order_delete, order_id, order_item_input, delete_account, delete_account_id;
    switch (action) {
        case "update-profile":
            status_btn_profile.textContent = 'Edit';
            Array.from(inputs).forEach(input => {
                if (input.name !== 'email') {
                    input.classList.toggle('active');
                }
            });
            break;
        case "create-supplier":
            window.location.href = 'supplier';
            break;
        case "create-account":
            window.location.href = 'account';
            break;
        case "delete-supplier":
            // Back
            if (typeBox) {
                document.querySelector('.' + typeBox + '').style.display = 'none';
            } else {
                // Do detete supplier 
                if (supplier_delete) {
                    const supplier_delete_id = supplier_delete.querySelector('b');
                    const id = supplier_delete_id.textContent;
                    fetch('supplier?action=' + action + '' + '&id=' + id, {
                        method: 'post'
                    })
                            .then(response => response.text())
                            .then(data => {
                                supplier_delete.style.display = 'none';
                                if (data === 'delete-supplier-success') {
                                    box_modal_delete.style.display = 'flex';
                                } else {
                                    const supplier_wrong_id = document.querySelector('.supplier-wrong-id');
                                    supplier_wrong_id.style.display = 'flex';
                                    const p = supplier_wrong_id.querySelector('p');
                                    p.innerHTML = "Wrong ID";
                                }
                            })
                            .catch(error => console.log(error));
                }
            }

            break;
        case "delete-supplier-sccess":
            // After delete success, then reload current page
            box_modal_delete.style.display = 'none';
            const currPage = document.querySelector('.admin-main-content-body-paging li.active');
            const value = currPage.textContent;
            window.location.href = 'supplier?value=' + value;
            break;
        case "cancel-order":
            order_cancel = document.querySelector('.order-cancel');
            order_id = order_cancel.querySelector('input[name=id]').value;
            fetch(`order?action=${action}&id=${order_id}`, {
                method: 'post'
            })
                    .then(response => response.text())
                    .then(data => {
                        order_cancel.style.display = 'none';
                        if (data === 'cancel-order-success') {
                            const cancel_order_success = document.querySelector('.cancel-order-success');
                            cancel_order_success.style.display = 'flex';
                        } else {
                            const order_wrong_id = document.querySelector('.order-wrong-id');
                            order_wrong_id.style.display = 'flex';
                            const p = order_wrong_id.querySelector('p');
                            p.innerHTML = "There's something was wrong!";
                        }
                    })
                    .catch(error => console.log(error));
            break;
        case "cancel-order-success":
            const cancel_order_success = document.querySelector('.cancel-order-success');
            cancel_order_success.style.display = 'none';

            // Chang status of order on interface
            order_cancel = document.querySelector('.order-cancel');
            order_id = order_cancel.querySelector('input').value;
            order_item_input = document.querySelector(`.order-item input[value="${order_id}"]`);
            if (order_item_input) {
                const order_item = order_item_input.parentElement;
                if (order_item.classList.contains('order-item')) {
                    const status = order_item.querySelector('.order-item-upper-status');
                    status.innerHTML = 'Cancelled';
                    const order_delete_btn = order_item.querySelector('.order-item-lower-btn');
                    if (order_delete_btn.classList.contains('disable')) {
                        order_delete_btn.classList.remove('disable');
                    }
                    const order_active_btn = order_item.querySelector('.order-item-mid-btn');
                    if (!order_active_btn.classList.contains('active')) {
                        order_active_btn.classList.add('active');
                    }

                }
            }

            break;
        case "delete-order":
            // Do detete order 
            order_delete = document.querySelector('.order-delete');
            if (order_delete) {
                order_id = order_delete.querySelector('input[name=id]').value;
                console.log(order_id);
                fetch(`order?action=${action}&id=${order_id}`, {
                    method: 'post'
                })
                        .then(response => response.text())
                        .then(data => {
                            order_delete.style.display = 'none';
                            if (data === 'delete-order-success') {
                                const delete_order_success = document.querySelector('.delete-order-success');
                                delete_order_success.style.display = 'flex';
                            } else {
                                const order_wrong_id = document.querySelector('.order-wrong-id');
                                order_wrong_id.style.display = 'flex';
                                const p = order_wrong_id.querySelector('p');
                                p.innerHTML = "There's something was wrong!";
                            }
                        })
                        .catch(error => console.log(error));
            }

            break;
        case "delete-order-success":
            const delete_order_success = document.querySelector('.delete-order-success');
            delete_order_success.style.display = 'none';

            // Remove order on interface
            order_delete = document.querySelector('.order-delete');
            order_id = order_delete.querySelector('input').value;
            order_item_input = document.querySelector(`.order-item input[value="${order_id}"]`);

            if (order_item_input) {
                const order_item = order_item_input.parentElement;
                if (order_item.classList.contains('order-item')) {
                    order_item.remove();
                }
            }
            break;
        case "address-require":
            document.querySelector(`.${action}`).style.display = 'none';
            window.location.href = 'profile';
            break;
        case "delete-transaction":
            const box_delete_transaction = document.querySelector('.transaction-delete');
            if (box_delete_transaction) {
                box_delete_transaction.style.display = 'none';
                const input = box_delete_transaction.querySelector('input');
                const id = input.value;
                fetch(`transaction?action=${action}&id=${id}`, {
                    method: 'post'
                })
                        .then(response => response.text())
                        .then(data => {
                            if (data === 'delete-transaction-success') {
                                const delete_transaction_success = document.querySelector('.delete-transaction-success');
                                delete_transaction_success.style.display = 'flex';
                            } else {
                                const transaction_wrong = document.querySelector('.transaction-wrong');
                                transaction_wrong.style.display = 'flex';
                                const p = transaction_wrong.querySelector('p');
                                p.innerHTML = "There's something was wrong!";
                            }
                        })
                        .catch(error => console.log(error));
            }
            break;
        case "delete-transaction-success":
            const delete_transaction_success = document.querySelector('.delete-transaction-success');
            delete_transaction_success.style.display = 'none';

            const delete_transaction = document.querySelector('.transaction-delete');
            if (delete_transaction) {
                const input = delete_transaction.querySelector('input');
                const id = input.value;
                const transactionItem = document.querySelector(`.transaction-items input[value='${id}']`);
                if (transactionItem) {
                    const tr = transactionItem.closest("tr");
                    console.log(tr);
                    tr.remove();
                }
            }

            break;
        case "delete-account":
            delete_account = document.querySelector('.account-delete-id');
            if (delete_account) {
                delete_account_id = delete_account.querySelector('b').textContent;
                fetch(`account?action=delete-account&id=${delete_account_id}`, {
                    method: 'post'
                })
                        .then(response => response.text())
                        .then(data => {
                            delete_account.style.display = 'none';
                            if (data === 'delete-account-success') {
                                const delete_account_success = document.querySelector('.delete-account-success');
                                delete_account_success.style.display = 'flex';
                            } else {
                                const account_wrong = document.querySelector('.account-wrong-id');
                                account_wrong.style.display = 'flex';
                                const p = account_wrong.querySelector('p');
                                p.innerHTML = "There's something was wrong!";
                            }
                        })
                        .catch(error => console.log(error));
            }
            break;
        case "delete-account-success":
            const delete_account_success = document.querySelector('.delete-account-success');
            delete_account_success.style.display = 'none';

            // After delete success, then reload current page
            const currPageAccount = document.querySelector('.admin-main-content-body-paging li.active');
            const valueAccount = currPageAccount.textContent;
            window.location.href = 'account?value=' + valueAccount;
            break;
        default:
            // Close current box
            const box = document.querySelector(`.${typeBox}`);
            box.style.display = 'none';
            break;
}

}

//    Admin -  Supplier 

function searchSuppier(input) {
    let value = input.value;
    let action = "search";
    let method = "post";
    if (value === '') {
        value = "1";
        action = "paging";
        method = "get";
    }
    const url = "supplier?action=" + action + "&value=" + value;
    const tbody = document.querySelector('.admin-main-content-body-tbody');
    fetch(url, {method: method})
            .then(response => response.text())
            .then(data => {

                if (data === "emptyData") {
                    data = `<td colspan="7" style="text-align: center; font-size: 2rem;">Nothing in suppier found!</td>`;
                    tbody.innerHTML = data;
                } else {
                    tbody.innerHTML = data;
                }
            })
            .catch(error => console.log(error));

}

function pagingSupplier(li) {
    const pre_li = document.querySelector('.admin-main-content-body-paging li.active');
    if (pre_li)
        pre_li.classList.remove('active');
    li.classList.add('active');

    const value = li.textContent;
    const url = "supplier?action=paging&value=" + value;
    const tbody = document.querySelector('.admin-main-content-body-tbody');
    fetch(url)
            .then(response => response.text())
            .then(data => tbody.innerHTML = data)
            .catch(error => console.log(error));
}

function handelSupplier(e, action, id = 0) {
    switch (action) {
        case "delete-supplier":
            const supplier_delete = document.querySelector('.supplier-delete-id');
            if (supplier_delete) {
                const supplier_delete_id = supplier_delete.querySelector('b');
                supplier_delete_id.innerHTML = id;
                supplier_delete.style.display = 'flex';
            }
            break;
        default:
            Validation({
                form: '.' + action + '',
                url: '/admin/supplier?action=' + action,
                rules: [
                    Validation.isValidName('input[name=suppliername]'),
                    Validation.isValidName('input[name=contactname]'),
                    Validation.isValidEmail('input[name=email]'),
                    Validation.isValidPhone('input[name=phone]'),
                    Validation.isRequire('input[name=address]')
                ]
            });
}
}

//  Draw chart

let revenueChart;

function drawChart(year, chart) {
    const ctx = document.getElementById('salesChart').getContext('2d');
    const type = (chart === 'month') ? 'line' : 'bar';
    fetch(`chart?year=${year}&type=${chart}`, {method: 'post'})
            .then(response => response.json())
            .then(data => {
                // console.log(data);
                const labels = data.Labels; // Get labels from servlet
                const revenuesCoffee = data.Coffee; // Get revenue coffee from servlet
                const revenuesBakery = data.Bakery; // Get revenue coffee from servlet
                const revenuesMilkTea = data.MilkTea; // Get revenue coffee from servlet

                // If have a chart before then remove it
                if (revenueChart) {
                    revenueChart.destroy();
                }

                // Draw new chart
                revenueChart = new Chart(ctx, {
                    type: type,
                    data: {
                        labels: labels,
                        datasets: [
                            {
                                label: 'Coffee (VND)',
                                data: revenuesCoffee,
                                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                borderColor: 'rgba(255, 99, 132, 1)',
                                borderWidth: 2,
                                tension: 0.5
                            },
                            {
                                label: 'Bakery (VND)',
                                data: revenuesBakery,
                                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                                borderColor: 'rgba(54, 162, 235, 1)',
                                borderWidth: 2,
                                tension: 0.5
                            },
                            {
                                label: 'Milk Tea (VND)',
                                data: revenuesMilkTea,
                                backgroundColor: 'rgba(255, 206, 86, 0.2)',
                                borderColor: 'rgba(255, 206, 86, 1)',
                                borderWidth: 2,
                                tension: 0.5
                            }
                        ]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false // Không duy trì tỉ lệ cố định
                    }
                });
            })
            .catch(error => console.error('Error fetching data chart month:', error));
}

//           Admin -  Cart Item

function cartItem(item, id) {
    const totalPrice = item.querySelector('.cart-info-item-price').textContent.trim();
    const totalPriceVal = totalPrice.replace(/[^0-9]/g, '');
    const quantity = item.querySelector('.cart-info-item-quantity').textContent.trim();
    const sizeTopping = item.querySelector('.cart-info-item-sizeAndTopping').textContent.trim();
    const [size, ...toppings] = sizeTopping.split(";");
    const payment = item.querySelector('input').value;
    const data = {
        totalPrice: totalPriceVal,
        quantity: quantity,
        size: size,
        toppings: toppings,
        payment: payment
    };
    // Chuyển đổi object thành query string
    const params = new URLSearchParams(data).toString();
    const main = document.querySelector('#main');
    const url = `product?id=${id}&action=cartItem&${params}`;
    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });
    fetch(url)
            .then(response => response.text())
            .then(data => {
                if (data === "error-data") {
                    main.innerHTML = "Error data!";
                } else {
                    main.innerHTML = data;
                }
            })
            .catch(err => console.log(err));
}

//  Admin  - Bar

function showAdminBar() {
    const tab_control = document.querySelector('.admin-main-control');
    if (tab_control) {
        tab_control.style.display = 'block';
    }
}

//  Order  -  Paging

function OrderPaging(li) {
    const page = li.textContent;
    const preLi = document.querySelector('.order-paging li.active');
    if (preLi) {
        preLi.classList.remove('active');
    }
    li.classList.add('active');
    const items = document.querySelector('.order-items');
    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });
    fetch("order?action=paging&page=" + page)
            .then(response => response.text())
            .then(data => {
                items.innerHTML = data;
            })
            .catch(error => console.log(error));
}

function orderSearch(input) {
    const value = input.value;
    const items = document.querySelector('.order-items');
    fetch(`order?action=search&value=${value}`, {
        method: 'post'
    })
            .then(response => response.text())
            .then(data => {
                items.innerHTML = data;
            })
            .catch(error => console.log(error));
}

function deleteOrder(id, pName) {
    const box_order_delete = document.querySelector('.order-delete');
    const order_product_id = box_order_delete.querySelector('input');
    const order_product_name = box_order_delete.querySelector('b');
    order_product_id.value = id;
    order_product_name.textContent = pName;
    box_order_delete.style.display = 'flex';

}

function cancelOrder(id, pName) {
    const box_order_cancel = document.querySelector('.order-cancel');
    const order_id = box_order_cancel.querySelector('input');
    const order_product_name = box_order_cancel.querySelector('b');
    order_id.value = id;
    order_product_name.textContent = pName;
    box_order_cancel.style.display = 'flex';

}

// Transaction  -  Paging

function transactionPaging(li) {
    const page = li.textContent;
    const preLi = document.querySelector('.transaction-paging li.active');
    if (preLi) {
        preLi.classList.remove('active');
    }
    li.classList.add('active');
    const items = document.querySelector('.transaction-items tbody');
    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });
    fetch("transaction?action=paging&page=" + page)
            .then(response => response.text())
            .then(data => {
                items.innerHTML = data;
            })
            .catch(error => console.log(error));
}

function transactionSearch(input) {
    const value = input.value;
    const items = document.querySelector('.transaction-items tbody');
    fetch(`transaction?action=search&value=${value}`, {
        method: 'post'
    })
            .then(response => response.text())
            .then(data => {
                items.innerHTML = data;
            })
            .catch(error => console.log(error));
}

function deleteTransaction(id, date) {

    const box_delete_transaction = document.querySelector('.transaction-delete');
    if (box_delete_transaction) {
        const input = box_delete_transaction.querySelector('input');
        input.value = id;
        const b = box_delete_transaction.querySelector('b');
        b.textContent = date;
        box_delete_transaction.style.display = 'flex';
    }
}

//   duy

function searchAccount(input) {
    let value = input.value;
    let action = "search";
    let method = "post";
    if (value === '') {
        value = "1";
        action = "paging";
        method = "get";
    }
    const url = "account?action=" + action + "&value=" + value;
    const tbody = document.querySelector('.admin-main-content-body-tbody');
    fetch(url, {method: method})
            .then(response => response.text())
            .then(data => {

                if (data === "emptyData") {
                    data = `<td colspan="7" style="text-align: center; font-size: 2rem;">Nothing in account found!</td>`;
                    tbody.innerHTML = data;
                } else {
                    tbody.innerHTML = data;
                }
            })
            .catch(error => console.log(error));

}

function pagingAccount(li) {
    const pre_li = document.querySelector('.admin-main-content-body-paging li.active');
    if (pre_li)
        pre_li.classList.remove('active');
    li.classList.add('active');

    const value = li.textContent;
    const url = "account?action=paging&value=" + value;
    const tbody = document.querySelector('.admin-main-content-body-tbody');
    fetch(url)
            .then(response => response.text())
            .then(data => tbody.innerHTML = data)
            .catch(error => console.log(error));
}


function handleAccount(e, action, id = 0) {
    const account_delete = document.querySelector('.account-delete-id');
    if (account_delete) {
        const account_delete_id = account_delete.querySelector('b');
        account_delete_id.innerHTML = id;
        account_delete.style.display = 'flex';
}
}

//     Duy

/// Pham Quoc Tu
function handleCategory(category) {
    let elements = [
        "priceM", "priceL",
        "sizeM", "sizeL",
        "product_multiTopping"
    ];

    elements.forEach(id => {
        let el = document.getElementById(id);
        category.value === "2" ? el.setAttribute("disabled", true) : el.removeAttribute("disabled");
    });
}

{
    let elements = [
        "priceM", "priceL",
        "sizeM", "sizeL",
        "product_multiTopping"
    ];

    elements.forEach(id => {
        let el = document.getElementById(id);
        if (document.getElementById("product_category")) {
            document.getElementById("product_category").value === "2" ? el.setAttribute("disabled", true) : el.removeAttribute("disabled");
        }

    });
}

{

    if (document.getElementById("product_image")) {
        document.getElementById("product_image").addEventListener("change", function (event) {
            let file = event.target.files[0];
            let errorMsg = document.getElementById("fileError");
            let createBtn = document.getElementById("createBtn");

            if (!file) {
                errorMsg.textContent = "Please select a file!";
                createBtn.disabled = true;
                return;
            }

            let allowedTypes = ["image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp"];

            if (!allowedTypes.includes(file.type)) {
                errorMsg.textContent = "Only image files (JPG, PNG, GIF, BMP, WEBP) are allowed.";
                createBtn.disabled = true;
                return;
            }

            errorMsg.textContent = "";
            createBtn.disabled = false;

            // Hiển thị ảnh xem trước
            let reader = new FileReader();
            reader.onload = function (e) {
                let previewImage = document.getElementById("previewImage");
                if (!previewImage) {
                    previewImage = document.createElement("img");
                    previewImage.id = "previewImage";
                    previewImage.width = 150;
                    document.getElementById("product_image").parentNode.appendChild(previewImage);
                }
                previewImage.src = e.target.result;
            };
            reader.readAsDataURL(file);
        });
    }

}

function handleSizePrice(size) {
    let priceInput = document.getElementById("price" + size.value.charAt(0)); // Tìm input tương ứng

    if (size.checked) {
        priceInput.type = "number"; // Hiển thị input khi checkbox được chọn
    } else {
        priceInput.type = "hidden"; // Ẩn input khi checkbox bị bỏ chọn
    }
}
function searchProduct(input) {
    const value = input.value;
    const url = "product?action=search&value=" + value;
    const tbody = document.querySelector('.admin-main-content-body-tbody');
    fetch(url)
            .then(response => response.text())
            .then(data => tbody.innerHTML = data)
            .catch(error => console.log(error));
}

function pagingProduct(li) {
    const pre_li = document.querySelector('.admin-main-content-body-paging li.active');
    if (pre_li)
        pre_li.classList.remove('active');
    li.classList.add('active');

    const value = li.textContent;
    const url = "product?action=paging&value=" + value;
    const tbody = document.querySelector('.admin-main-content-body-tbody');
    fetch(url)
            .then(response => response.text())
            .then(data => tbody.innerHTML = data)
            .catch(error => console.log(error));
}

function searchOrder(input) {
    const value = input.value;
    const url = "order?action=searchOrder&value=" + value;
    const tbody = document.querySelector('.admin-main-content-body-tbody');
    fetch(url)
            .then(response => response.text())
            .then(data => tbody.innerHTML = data)
            .catch(error => console.log(error));
}

function pagingOrder(li) {
    const pre_li = document.querySelector('.admin-main-content-body-paging li.active');
    if (pre_li)
        pre_li.classList.remove('active');
    li.classList.add('active');

    const value = li.textContent;
    const url = "order?action=pagingOrder&value=" + value;
    const tbody = document.querySelector('.admin-main-content-body-tbody');
    fetch(url)
            .then(response => response.text())
            .then(data => tbody.innerHTML = data)
            .catch(error => console.log(error));
}
/// Pham Quoc Tu

//  Comemnt

function addComment() {
    const form = document.querySelector('.form-review-container');
    if (form) {
        form.style.display = 'block';
    }
}

function doComment(e) {
    e.preventDefault();
    const form = document.querySelector('.form-review-container');
    const formData = new FormData(form);
    let isValid = true;

    // Check all input, textarea in form
    for (const [key, value] of formData.entries()) {
        const trimmedValue = value.trim();
        if (trimmedValue === "") {
            alert(`Please fill in the ${key} field.`);
            isValid = false;
            break;
        }

        // Check if rating is between 0 and 5
        if (key === "rating") {
            const ratingValue = Number(trimmedValue);
            if (isNaN(ratingValue) || ratingValue < 0 || ratingValue > 5) {
                alert("Rating must be a number between 0 and 5.");
                isValid = false;
                break;
            }
        }
    }


    if (!isValid)
        return;
    fetch(`comment?action=create`, {
        method: 'post',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: new URLSearchParams([...formData])
    })
            .then(response => {
                if (response.status === 401) {
                    // Nếu nhận HTTP 401 (chưa đăng nhập), chuyển hướng về login
                    window.location.href = "login";
                    throw new Error("User not logged in");
                }
                return response.json();
            })
            .then(datas => {
                console.log(datas);
                if (datas.fail) {
                    alert(`Remove a review fail!`);
                } else if (datas.limit) {
                    alert(`Your number of comments has reached the daily limit!`);
                } else {
                    const product_reviews = document.querySelector('.product-reviews');
                    alert(`Create a review successfully.`);
                    const stars = Array(5)
                            .fill(0)
                            .map((_, i) =>
                                    `<i class="fa fa-star ${i < datas.rating ? 'active' : ''}"></i>`
                            )
                            .join('');
                    const html = `<div class="product-review-body">
                                    <div>
                                        <div class="product-review-header">
                                            <div class="product-review-user">
                                                <img class="avatar" src="${datas.img}" referrerpolicy="no-referrer" alt="avatar">                                </div>
                                            <div class="product-review-info">
                                                <div class="product-review-name">
                                                    user2
                                                </div>
                                                <div class="product-review-ex">
                                                    <div class="product-review-date">
                                                        ${datas.date}
                                                    </div>
                                                    <div class="product-review-start">
                                                        ${stars}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="product-review-content">
                                            <span>${datas.comment}</span>
                                        </div>
                                    </div>
                                    <div class="product-review-remove">
                                        <button class="bttn product-review-remove" onclick="deleteComment(this, '${datas.rId}')">
                                            <span><i class="fa-solid fa-trash"></i></span>
                                        </button>
                                    </div>                      
                                </div>`;

                    product_reviews.insertAdjacentHTML("beforeend", html);
                    form.reset();

                }
            })
            .catch(error => console.log(error));
}

function deleteComment(btn, id) {
    const answer = confirm(`Do you want to delete comment with id ${id}!`);
    if (answer) {
        fetch(`comment?action=delete&id=${id}`, {method: 'post'})
                .then(response => response.text())
                .then(data => {
                    if (data === 'success') {
                        alert(`Remove a review successfully.`);
                        const review = btn.closest('.product-review-body');
                        if (review)
                            review.remove();
                    } else {
                        alert(`Remove a review fail!`);
                    }
                })
                .catch(error => console.log(error));
    }
}