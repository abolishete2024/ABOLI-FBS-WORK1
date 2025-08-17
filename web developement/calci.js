function calculate(op) {
    let num1 = parseFloat(document.getElementById("num1").value);
    let num2 = parseFloat(document.getElementById("num2").value);
    let result = 0;

    if (isNaN(num1) || isNaN(num2)) {
        document.getElementById("result").innerText = "Result: Please enter valid numbers";
        return;
    }

    if (op === '+') result = num1 + num2;
    else if (op === '-') result = num1 - num2;
    else if (op === '*') result = num1 * num2;
    else if (op === '/') {
        if (num2 === 0) {
            document.getElementById("result").innerText = "Result: Cannot divide by zero";
            return;
        }
        result = num1 / num2;
    }

    document.getElementById("result").innerText = "Result: " + result;
}
