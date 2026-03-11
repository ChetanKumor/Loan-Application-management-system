// Wait for the DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // Set up event listeners
    setupEventListeners();
    
    // Initial calculation
    calculateLoan();
});

// Set up all event listeners
function setupEventListeners() {
    // Connect sliders to input fields
    connectInputAndSlider('loan-amount', 'loan-amount-slider');
    connectInputAndSlider('interest-rate', 'interest-rate-slider');
    connectInputAndSlider('loan-term', 'loan-term-slider');
    
    // Calculate loan on button click
    document.getElementById('calculate-btn').addEventListener('click', calculateLoan);
    
    // Toggle amortization schedule
    document.getElementById('show-amortization').addEventListener('click', toggleAmortizationTable);
    
    // Tab switching
    const tabButtons = document.querySelectorAll('.tab-btn');
    tabButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Remove active class from all buttons
            tabButtons.forEach(btn => btn.classList.remove('active'));
            // Add active class to clicked button
            this.classList.add('active');
            
            // Update calculator for loan type
            updateCalculatorForLoanType(this.dataset.tab);
        });
    });
}

// Connect input field and slider
function connectInputAndSlider(inputId, sliderId) {
    const input = document.getElementById(inputId);
    const slider = document.getElementById(sliderId);
    
    // Update input when slider changes
    slider.addEventListener('input', function() {
        input.value = this.value;
    });
    
    // Update slider when input changes
    input.addEventListener('input', function() {
        slider.value = this.value;
    });
}

// Format currency to Indian Rupee format
function formatCurrency(amount) {
    // Hard-coded Rupee symbol
    let rupeeSymbol = '₹';
    
    // Format number to 2 decimal places
    let formattedAmount = amount.toFixed(2);
    
    // Add commas for thousands separator (Indian format)
    let [wholePart, decimalPart] = formattedAmount.split('.');
    let lastThree = wholePart.substring(wholePart.length - 3);
    let otherNumbers = wholePart.substring(0, wholePart.length - 3);
    
    if (otherNumbers !== '') {
        lastThree = ',' + lastThree;
    }
    
    let formattedWholePart = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ',') + lastThree;
    
    // Return the formatted string with Rupee symbol
    return rupeeSymbol + formattedWholePart + '.' + decimalPart;
}

// Calculate loan
function calculateLoan() {
    const loanAmount = parseFloat(document.getElementById('loan-amount').value);
    const interestRate = parseFloat(document.getElementById('interest-rate').value) / 100 / 12; // Monthly interest rate
    const loanTerm = parseInt(document.getElementById('loan-term').value) * 12; // Term in months
    
    // Calculate monthly payment
    const x = Math.pow(1 + interestRate, loanTerm);
    const monthlyPayment = (loanAmount * x * interestRate) / (x - 1);
    
    // Calculate total payment and interest
    const totalPayment = monthlyPayment * loanTerm;
    const totalInterest = totalPayment - loanAmount;
    
    // Update the results with Indian Rupee format
    document.getElementById('monthly-payment').innerHTML = formatCurrency(monthlyPayment);
    document.getElementById('total-payment').innerHTML = formatCurrency(totalPayment);
    document.getElementById('total-interest').innerHTML = formatCurrency(totalInterest);
    
    // Generate amortization schedule
    generateAmortizationSchedule(loanAmount, interestRate, loanTerm, monthlyPayment);
}

// Generate amortization schedule
function generateAmortizationSchedule(loanAmount, interestRate, loanTerm, monthlyPayment) {
    const tableBody = document.getElementById('amortization-body');
    tableBody.innerHTML = '';
    
    let balance = loanAmount;
    
    for (let i = 1; i <= loanTerm; i++) {
        const interest = balance * interestRate;
        const principal = monthlyPayment - interest;
        balance -= principal;
        
        // Create table row with Indian Rupee format
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${i}</td>
            <td>${formatCurrency(monthlyPayment)}</td>
            <td>${formatCurrency(principal)}</td>
            <td>${formatCurrency(interest)}</td>
            <td>${formatCurrency(Math.max(0, balance))}</td>
        `;
        
        tableBody.appendChild(row);
    }
}

// Toggle amortization table visibility
function toggleAmortizationTable() {
    const table = document.getElementById('amortization-table');
    const button = document.getElementById('show-amortization');
    const icon = button.querySelector('i');
    const text = button.querySelector('span');
    
    if (table.style.display === 'none') {
        table.style.display = 'block';
        icon.classList.remove('fa-chevron-down');
        icon.classList.add('fa-chevron-up');
        text.textContent = 'Hide Amortization Schedule';
    } else {
        table.style.display = 'none';
        icon.classList.remove('fa-chevron-up');
        icon.classList.add('fa-chevron-down');
        text.textContent = 'Show Amortization Schedule';
    }
}

// Update calculator parameters based on loan type
function updateCalculatorForLoanType(loanType) {
    const loanAmountSlider = document.getElementById('loan-amount-slider');
    const loanAmountInput = document.getElementById('loan-amount');
    const interestRateSlider = document.getElementById('interest-rate-slider');
    const interestRateInput = document.getElementById('interest-rate');
    const loanTermSlider = document.getElementById('loan-term-slider');
    const loanTermInput = document.getElementById('loan-term');
    
    switch(loanType) {
        case 'personal':
            // Personal loan defaults
            loanAmountSlider.min = '50000';
            loanAmountSlider.max = '5000000';
            loanAmountSlider.value = '500000';
            loanAmountInput.value = '500000';
            
            interestRateSlider.value = '12';
            interestRateInput.value = '12';
            
            loanTermSlider.max = '7';
            loanTermSlider.value = '3';
            loanTermInput.value = '3';
            break;
            
        case 'home':
            // Home loan defaults
            loanAmountSlider.min = '500000';
            loanAmountSlider.max = '10000000';
            loanAmountSlider.value = '3000000';
            loanAmountInput.value = '3000000';
            
            interestRateSlider.value = '8';
            interestRateInput.value = '8';
            
            loanTermSlider.max = '30';
            loanTermSlider.value = '20';
            loanTermInput.value = '20';
            break;
            
        case 'auto':
            // Auto loan defaults
            loanAmountSlider.min = '100000';
            loanAmountSlider.max = '2000000';
            loanAmountSlider.value = '800000';
            loanAmountInput.value = '800000';
            
            interestRateSlider.value = '9';
            interestRateInput.value = '9';
            
            loanTermSlider.max = '7';
            loanTermSlider.value = '5';
            loanTermInput.value = '5';
            break;
            
        case 'education':
            // Education loan defaults
            loanAmountSlider.min = '100000';
            loanAmountSlider.max = '5000000';
            loanAmountSlider.value = '1500000';
            loanAmountInput.value = '1500000';
            
            interestRateSlider.value = '10';
            interestRateInput.value = '10';
            
            loanTermSlider.max = '15';
            loanTermSlider.value = '7';
            loanTermInput.value = '7';
            break;
    }
    
    // Recalculate with new values
    calculateLoan();
}