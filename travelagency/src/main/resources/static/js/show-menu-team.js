const hiddenTeam = document.querySelector('.hidden-team');
const team = document.querySelector('#team');

team.addEventListener('mouseover', () => {
    hiddenTeam.style.display = 'block';
});

team.addEventListener('mouseout', () => {
    hiddenTeam.style.display = 'none';
});