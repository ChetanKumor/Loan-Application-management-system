// Function to handle the final form submission
function submitLoanApplication() {
    try {
        // Show loading indicator
        const submitButton = document.getElementById('submit-button') || document.getElementById('submit-application');
        const loadingIndicator = document.getElementById('loading-indicator') || document.getElementById('loading-spinner');
        const statusMessage = document.getElementById('status-message');
        
        if (submitButton) submitButton.disabled = true;
        if (loadingIndicator) loadingIndicator.style.display = 'block';
        if (statusMessage) {
            statusMessage.textContent = '';
            statusMessage.style.display = 'none';
        }
        
        // Debug: Log all localStorage keys and values
        console.log('All localStorage items:');
        for (let i = 0; i < localStorage.length; i++) {
            const key = localStorage.key(i);
            console.log(`Key: ${key}, Value: ${localStorage.getItem(key)}`);
        }
        
        // Get street address and city for full address
        const streetAddress = localStorage.getItem('personalLoanStreetAddress') || '';
        const city = localStorage.getItem('personalLoanCity') || '';
        const fullAddress = streetAddress + (city ? `, ${city}` : '');
        
        // Collect all data from localStorage with the correct keys
        const loanData = {
            purpose: localStorage.getItem('personalLoanPurpose') || '',
            amount: parseFloat(localStorage.getItem('personalLoanAmount') || '0'),
            dob: localStorage.getItem('personalLoanDob') || '',  // Make sure this matches what's stored
            housingStatus: localStorage.getItem('personalLoanHousing') || '',
            creditScore: localStorage.getItem('personalLoanCredit') || '',  // This should match backend expectations
            employmentStatus: localStorage.getItem('personalLoanEmployment') || '',
            income: parseFloat(localStorage.getItem('personalLoanIncome') || '0'),
            pincode: localStorage.getItem('personalLoanPincode') || '',
            address: fullAddress,
            fullName: `${localStorage.getItem('personalLoanFirstName') || ''} ${localStorage.getItem('personalLoanLastName') || ''}`.trim(),
            phone: localStorage.getItem('personalLoanPhone') || '',
            email: localStorage.getItem('personalLoanEmail') || ''
        };

        // Log the exact JSON being sent to the server
        console.log('JSON being sent to server:', JSON.stringify(loanData));

        // Validate required fields
        const requiredFields = ['purpose', 'amount', 'dob', 'housingStatus', 'creditScore', 
                               'employmentStatus', 'income', 'address', 'fullName', 
                               'phone', 'email'];

        const missingFields = requiredFields.filter(field => 
            !loanData[field] || 
            (typeof loanData[field] === 'string' && loanData[field].trim() === '') ||
            (typeof loanData[field] === 'number' && loanData[field] === 0)
        );

        if (missingFields.length > 0) {
            console.error('Missing required fields:', missingFields);
            throw new Error(`Missing required fields: ${missingFields.join(', ')}`);
        }
        
        // Send data to the backend
        fetch('http://localhost:8080/api/personal-loan/apply', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(loanData)
        })
        .then(response => {
            console.log('API Response status:', response.status);
            console.log('API Response headers:', [...response.headers].map(h => `${h[0]}: ${h[1]}`).join(', '));
            
            if (!response.ok) {
                return response.text().then(text => {
                    console.error('Error response body:', text);
                    throw new Error(`Server error (${response.status}): ${text || 'No error details provided'}`);
                });
            }
            
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            
            // Hide loading indicator
            if (loadingIndicator) loadingIndicator.style.display = 'none';
            
            // Show success message
            if (statusMessage) {
                statusMessage.textContent = 'Application submitted successfully!';
                statusMessage.className = 'status-message success';
                statusMessage.style.display = 'block';
            }
            
            // Clear localStorage
            for (const key in localStorage) {
                if (key.startsWith('personalLoan')) {
                    localStorage.removeItem(key);
                }
            }
            
            // Redirect to thank you page after a delay with the application ID
            setTimeout(() => {
                window.location.href = 'thank-you.html?id=' + (data.id || '');
            }, 1500);
        })
        .catch(error => {
            console.error('Error:', error);
            
            // Hide loading indicator
            if (loadingIndicator) loadingIndicator.style.display = 'none';
            
            // Show error message
            if (statusMessage) {
                statusMessage.textContent = error.message;
                statusMessage.className = 'status-message error';
                statusMessage.style.display = 'block';
            } else {
                alert('Error: ' + error.message);
            }
            
            if (submitButton) submitButton.disabled = false;
        });
    } catch (error) {
        console.error('Error preparing data:', error);
        
        const statusMessage = document.getElementById('status-message');
        if (statusMessage) {
            statusMessage.textContent = 'An error occurred preparing your data. Please try again.';
            statusMessage.className = 'status-message error';
            statusMessage.style.display = 'block';
        } else {
            alert('An error occurred preparing your data. Please try again.');
        }
        
        const submitButton = document.getElementById('submit-button') || document.getElementById('submit-application');
        if (submitButton) submitButton.disabled = false;
        
        const loadingIndicator = document.getElementById('loading-indicator') || document.getElementById('loading-spinner');
        if (loadingIndicator) loadingIndicator.style.display = 'none';
    }
}

// Add event listener when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    // Check if we're on the confirmation page
    const submitButton = document.getElementById('submit-application');
    if (submitButton) {
        submitButton.addEventListener('click', submitLoanApplication);
        
        // Populate application details if needed
        if (typeof populateApplicationDetails === 'function') {
            populateApplicationDetails();
        }
    }
});