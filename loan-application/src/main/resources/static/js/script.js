document.addEventListener('DOMContentLoaded', function() {
    // Product navigation functionality
    const productCards = document.querySelectorAll('.product-card');
    const productSections = document.querySelectorAll('.product-section');
    
    productCards.forEach(card => {
        card.addEventListener('click', function() {
            // Remove active class from all cards
            productCards.forEach(c => c.classList.remove('active'));
            
            // Add active class to clicked card
            this.classList.add('active');
            
            // Hide all product sections
            productSections.forEach(section => {
                section.classList.remove('active');
            });
            
            // Show the corresponding section
            const targetSection = this.getAttribute('data-section');
            if (targetSection) {
                document.getElementById(targetSection).classList.add('active');
            }
        });
    });
    
    // Insurance type selection
    const insuranceTypes = document.querySelectorAll('.insurance-type');
    
    insuranceTypes.forEach(type => {
        type.addEventListener('click', function() {
            // Remove selected class from all types
            insuranceTypes.forEach(t => {
                t.classList.remove('selected');
                const indicator = t.querySelector('.selection-indicator');
                if (indicator) {
                    indicator.classList.remove('active');
                }
            });
            
            // Add selected class to clicked type
            this.classList.add('selected');
            const indicator = this.querySelector('.selection-indicator');
            if (indicator) {
                indicator.classList.add('active');
            }
        });
    });
    
    // Loan Comparison Navigation Functionality
    const compareRatesBtn = document.getElementById('compare-rates-btn');
    
    if (compareRatesBtn) {
        compareRatesBtn.addEventListener('click', function(e) {
            e.preventDefault();
            // Navigate to the loan type page (changed from loan-purpose.html)
            window.location.href = 'loan-type.html';
        });
    }
    
    // Universal Accordion functionality for ALL loan pages
    function initAccordions() {
        const accordionHeaders = document.querySelectorAll('.accordion-header');
        console.log('Found ' + accordionHeaders.length + ' accordion headers');
        
        accordionHeaders.forEach(header => {
            // Remove existing event listeners by cloning and replacing
            const newHeader = header.cloneNode(true);
            header.parentNode.replaceChild(newHeader, header);
            
            newHeader.addEventListener('click', function() {
                console.log('Accordion clicked');
                const accordionItem = this.parentElement;
                
                // Toggle active class
                accordionItem.classList.toggle('active');
                
                // Toggle icon if it exists
                const icon = this.querySelector('.accordion-icon i');
                if (icon) {
                    if (accordionItem.classList.contains('active')) {
                        icon.classList.remove('fa-plus');
                        icon.classList.add('fa-minus');
                    } else {
                        icon.classList.remove('fa-minus');
                        icon.classList.add('fa-plus');
                    }
                }
                
                // Toggle content visibility
                const content = this.nextElementSibling;
                if (content && content.classList.contains('accordion-content')) {
                    if (accordionItem.classList.contains('active')) {
                        content.style.maxHeight = content.scrollHeight + "px";
                    } else {
                        content.style.maxHeight = null;
                    }
                }
            });
        });
    }
    
    // Initialize accordions when DOM is loaded
    initAccordions();
    
    // Also initialize accordions when page is fully loaded (for any dynamically added content)
    window.addEventListener('load', initAccordions);
    
    // Testimonial slider functionality
    const slider = document.querySelector('.testimonial-slider');
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    const cardWidth = 320; // Card width + gap
    
    if (prevBtn && nextBtn && slider) {
        prevBtn.addEventListener('click', function() {
            slider.scrollBy({
                left: -cardWidth,
                behavior: 'smooth'
            });
        });
        
        nextBtn.addEventListener('click', function() {
            slider.scrollBy({
                left: cardWidth,
                behavior: 'smooth'
            });
        });
    }
});