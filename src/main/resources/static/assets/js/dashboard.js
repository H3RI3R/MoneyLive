function toggleNav() {
    const sidebar = document.getElementById("mySidebar");
    const main = document.getElementById("main");
    sidebar.classList.toggle("open");
    if (sidebar.classList.contains("open")) {
        main.style.marginLeft = "250px"; // Adjust content when sidebar opens
    } else {
        main.style.marginLeft = "0"; // Reset margin when sidebar closes
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const currentLocation = window.location.pathname;  // Gets the current page's file path
    const links = document.querySelectorAll('.sidebar-elements a');

    links.forEach(link => {
        const linkPath = new URL(link.href).pathname;  // Get the pathname of the link
        
        if (currentLocation === linkPath) {
            link.classList.add('active');  // Add active class if the paths match
        } else {
            link.classList.remove('active');  // Remove the class from other links
        }
    });
});