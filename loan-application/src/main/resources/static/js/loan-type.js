document.addEventListener('DOMContentLoaded', function() {
    const continueBtn = document.getElementById('continue-btn');
    const loanPurposeSelect = document.getElementById('loan-purpose-select');
    
    if (continueBtn && loanPurposeSelect) {
        continueBtn.addEventListener('click', function() {
            const selectedLoanType = loanPurposeSelect.value;
            
            // Navigate to the appropriate loan page based on selection
            if (selectedLoanType) {
                let targetPage = '';
                
                switch (selectedLoanType) {
                    case 'personal':
                        targetPage = 'personal-loan.html';
                        break;
                    case 'home':
                        targetPage = 'home-loan.html';
                        break;
                    case 'education':
                        targetPage = 'education-loan.html';
                        break;
                    case 'mortgage':
                        targetPage = 'mortgage-loan.html';
                        break;
                    case 'vehicle':
                        targetPage = 'vehicle-loan.html';
                        break;
                    default:
                        // Default to homepage if somehow an invalid option is selected
                        targetPage = 'homepage.html';
                }
                
                // Navigate to the selected loan page
                window.location.href = targetPage;
            } else {
                // If no loan type is selected, show an alert
                alert('Please select a loan type before continuing.');
            }
        });
    }
    
    // Back to home button functionality
    const backToHomeBtn = document.getElementById('back-to-home');
    if (backToHomeBtn) {
        backToHomeBtn.addEventListener('click', function(e) {
            e.preventDefault();
            window.location.href = 'homepage.html';
        });
    }
});