// Loan application form submission handler
document.addEventListener('DOMContentLoaded', function() {
    // Check if we're on a form page with a submit button
    const submitButton = document.querySelector('.btn-primary[type="submit"]');
    
    if (submitButton) {
        const form = submitButton.closest('form');
        
        if (form) {
            form.addEventListener('submit', function(e) {
                e.preventDefault();
                
                // Show loading indicator if it exists
                const loadingSpinner = document.getElementById('loading-spinner');
                if (loadingSpinner) {
                    loadingSpinner.style.display = 'block';
                }
                
                // Clear any previous status messages
                const statusMessage = document.getElementById('status-message');
                if (statusMessage) {
                    statusMessage.textContent = '';
                    statusMessage.style.display = 'none';
                }
                
                // Get form data
                const formData = new FormData(form);
                const loanData = {};
                
                // Convert FormData to JSON object
                for (let [key, value] of formData.entries()) {
                    loanData[key] = value;
                }
                
                // Add any data from localStorage
                for (let i = 0; i < localStorage.length; i++) {
                    const key = localStorage.key(i);
                    if (key.startsWith('personalLoan')) {
                        loanData[key] = localStorage.getItem(key);
                    }
                }
                
                // Format the data according to the backend model requirements
                const formattedData = {
                    purpose: loanData.personalLoanPurpose || loanData.purpose || '',
                    amount: loanData.personalLoanAmount || loanData.amount || '',
                    dob: loanData.personalLoanDob || loanData.dob || '',
                    housingStatus: loanData.personalLoanHousing || loanData.housingStatus || '',
                    creditScore: loanData.personalLoanCredit || loanData.creditScore || '',
                    employmentStatus: loanData.personalLoanEmployment || loanData.employmentStatus || '',
                    income: loanData.personalLoanIncome || loanData.income || '',
                    pincode: loanData.personalLoanPincode || loanData.pincode || '',
                    address: (loanData.personalLoanStreetAddress || loanData.address || '') + 
                             (loanData.personalLoanCity ? `, ${loanData.personalLoanCity}` : ''),
                    fullName: `${loanData.personalLoanFirstName || ''} ${loanData.personalLoanLastName || ''}`,
                    phone: loanData.personalLoanPhone || loanData.phone || '',
                    email: loanData.personalLoanEmail || loanData.email || ''
                };
                
                console.log('Formatted data for API:', formattedData);
                
                // Validate the data before sending
                const missingFields = [];
                Object.entries(formattedData).forEach(([key, value]) => {
                    if (!value) {
                        missingFields.push(key);
                    }
                });
                
                if (missingFields.length > 0) {
                    console.error('Missing fields:', missingFields);
                    if (loadingSpinner) loadingSpinner.style.display = 'none';
                    if (statusMessage) {
                        statusMessage.textContent = `Missing required fields: ${missingFields.join(', ')}`;
                        statusMessage.className = 'status-message error';
                        statusMessage.style.display = 'block';
                    } else {
                        alert(`Missing required fields: ${missingFields.join(', ')}`);
                    }
                    return;
                }
                
                // Send data to backend with absolute URL
                fetch('http://localhost:8080/api/personal-loan/apply', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify(formattedData)
                })
                .then(response => {
                    console.log('API Response status:', response.status);
                    
                    if (!response.ok) {
                        return response.text().then(text => {
                            console.error('Error response body:', text);
                            throw new Error(`Server error (${response.status}): ${text}`);
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Success:', data);
                    if (loadingSpinner) loadingSpinner.style.display = 'none';
                    
                    if (statusMessage) {
                        statusMessage.textContent = 'Loan application submitted successfully!';
                        statusMessage.className = 'status-message success';
                        statusMessage.style.display = 'block';
                    } else {
                        alert('Loan application submitted successfully!');
                    }
                    
                    // Redirect to success page after a short delay
                    setTimeout(() => {
                        window.location.href = 'thank-you.html?id=' + (data.id || '');
                    }, 1500);
                })
                .catch(error => {
                    console.error('Error:', error);
                    if (loadingSpinner) loadingSpinner.style.display = 'none';
                    
                    if (statusMessage) {
                        statusMessage.textContent = error.message;
                        statusMessage.className = 'status-message error';
                        statusMessage.style.display = 'block';
                    } else {
                        alert('Error submitting loan application: ' + error.message);
                    }
                });
            });
        }
    }
});