const observer = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('visible');
            observer.unobserve(entry.target);
        }
    });
}, {
    threshold: 0.5
});
  
const elements = document.querySelectorAll('.section-title');
elements.forEach(element => observer.observe(element));