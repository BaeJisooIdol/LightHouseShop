/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function Validation(options) {

    let isAllValid = true;
    function checkInputs(rule, inputElement, message) {
        const errorMessage = rule.test(inputElement.value);
        if (errorMessage) {
            message.innerText = errorMessage;
        } else {
            message.innerText = '';
        }
        return !errorMessage;
    }

    const formElement = document.querySelector(options.form);
    if (formElement) {
        options.rules.forEach((rule) => {
            const inputElement = formElement.querySelector(rule.select);
            const message = inputElement.parentElement.querySelector('.message');
            inputElement.oninput = function () {
                message.innerText = '';
            };
        });
    }


    formElement.onsubmit = function (e) {
        if (render_error) {
            render_error.textContent = '';
            render_error.style.animation = 'none';
            setTimeout(() => {
                render_error.style.animation = 'toRight linear .3s';
            }, 10);
        }
        isAllValid = true;
        e.preventDefault();
        options.rules.forEach((rule) => {
            const inputElement = formElement.querySelector(rule.select);
            const message = inputElement.parentElement.querySelector('.message');
            if (inputElement) {
                let check = true;
                check = checkInputs(rule, inputElement, message);
                if (!check) {
                    isAllValid = false;
                }
            }
        });
        if (isAllValid) {
            const formData = new FormData(formElement);
            fetch('/lighthouse/' + options.url, {
                method: 'POST',
                body: new URLSearchParams([...formData])
            })
                    .then(response => response.text())
                    .then(data => {
                        switch (data) {
                            case "userNameExisted":
                                render_error.textContent = 'The username already exists. Please choose another!';
                                break;
                            case "emailExisted":
                                render_error.textContent = 'Email already exists. Please choose another!';
                                break;
                            case "update-profile-success":
                                box_modal.style.display = 'flex';
                                const profile_input_username = document.querySelector('.profile-form-group input[name="username"]');
                                if (profile_input_username) {
                                    const user_name = document.querySelector('.user-name');
                                    if (user_name)
                                        user_name.textContent = profile_input_username.value;
                                }

                                break;
                            case "create-supplier-success":
                                const create_supplier_success = document.querySelector('.create-supplier-success');
                                create_supplier_success.style.display = 'flex';
                                break;
                            case "edit-supplier-success":
                                const edit_supplier_success = document.querySelector('.edit-supplier-success');
                                console.log(edit_supplier_success);
                                edit_supplier_success.style.display = 'flex';
                                break;
                            case "email-not-found":
                                // Handel reset password, but email not exist
                                render_error.textContent = 'Email is not exists. Please try again!';
                                break;
                            default:
                                acc_header.style.animation = 'none';
                                acc_body.style.animation = 'none';
                                setTimeout(() => {
                                    acc_header.style.animation = 'toRight ease-in-out .5s';
                                    acc_body.style.animation = 'toRight ease-in-out .5s';
                                }, 10);
                                acc_body.innerHTML = data;
                                // if(data.includes("Verify Your Email To Reset PassWord")) discruptSpan();
                                discruptSpan();
                                break;
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
        }
    };
}

Validation.isRequire = function (selection) {
    return {
        select: selection,
        test: function (value) {
            return value !== '' ? undefined : "You must enter into this field!";
        }
    };
};

Validation.isValidName = function (selection) {
    return {
        select: selection,
        test: function (value) {
            // Regular expression to allow only valid characters
            const regex = /^[^~!@#$%^&*()_+=/?|\\`,. ]*$/;
            return regex.test(value) ? undefined : "Input can't contain special characters!"; // ~!@#$%^&*()_+=/?|\\`,.
        }
    };
};

Validation.isValidEmail = function (selection) {
    return {
        select: selection,
        test: function (value) {
            // Regular expression to allow test email
            const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return regex.test(value) ? undefined : "Email is invalid. Please try again!";
        }
    };
};

Validation.isValidPhone = function (selection) {
    return {
        select: selection,
        test: function (value) {
            // Regular expression to allow test email
            const regex = /^[0-9]+$/;
            return regex.test(value) ? undefined : "Phone is invalid. Please try again!";
        }
    };
};

Validation.isValidPassword = function (selection, number) {
    return {
        select: selection,
        test: function (value) {
            return value.length >= number ? undefined : "Password must be at least " + number + " characters!";
        }
    };
};

Validation.confirmPassword = function (selection, password) {
    return {
        select: selection,
        test: function (value) {
            return value === password() ? undefined : "Passwords do not match!";
        }
    };
};