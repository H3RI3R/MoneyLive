

document.addEventListener('DOMContentLoaded', function() {
    const userEmail = sessionStorage.getItem('userEmail');
    document.getElementById('userEmail').innerText = userEmail;
    document.getElementById('userEmail1').innerText = userEmail;});

    document.querySelector('.toggle-sidebar-btn').addEventListener('click', function() {
        const rowElement = document.querySelector('.row.justify-content-center');
        
        // Toggle the height between 100% and 125%
        if (rowElement.style.height === '125%') {
            rowElement.style.height = '115%';
        } else {
            rowElement.style.height = '125%';
        }
    });

//---------------------------------- Table  Api -----------------------------------
document.addEventListener('DOMContentLoaded', function() {
    const userEmail = sessionStorage.getItem('userEmail');

    if (userEmail) {
        const fetchUsersUrl = `http://localhost:8080/api/admin/distributor/users?adminEmail=${encodeURIComponent(userEmail)}`;

        fetch(fetchUsersUrl)
            .then(response => response.ok ? response.json() : Promise.reject('Unauthorized access'))
            .then(users => {
            users.sort((a, b) => (b.tokenAmount || 0) - (a.tokenAmount || 0));

            const retailerTableBody = document.getElementById('retailerTableBody');
            retailerTableBody.innerHTML = ''; // Clear existing rows

            users.forEach((user, index) => {
                const retailerRow = `
                        <tr>
                            <td>${index + 1}</td>
                            <td>${user.name || 'N/A'}</td>
                            <td>${user.email || 'N/A'}</td>
                            <td>${user.company || 'N/A'}</td>
                            <td>${user.tokenAmount !== undefined ? user.tokenAmount : 'N/A'}</td>
                            <td>${user.role || 'N/A'}</td>
                        </tr>
                    `;
                retailerTableBody.innerHTML += retailerRow;
            });
        })
            .catch(error => {
            console.error('Error:', error);
            document.getElementById('retailerTableBody').innerHTML = `
                    <tr>
                        <td colspan="6" class="text-center">Error fetching user data</td>
                    </tr>
                `;
        });
    } else {
        console.error('User email is not found in session storage.');
        document.getElementById('retailerTableBody').innerHTML = `
            <tr>
                <td colspan="6" class="text-center">User email not found</td>
            </tr>
        `;
    }
});


//---------------------------------- toggleSidebar  method -----------------------------------
/**
* Template Name: NiceAdmin
* Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
* Updated: Apr 20 2024 with Bootstrap v5.3.3
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/

(function() {
    "use strict";

    /**
     * Easy selector helper function
     */
    const select = (el, all = false) => {
        el = el.trim()
        if (all) {
            return [...document.querySelectorAll(el)]
        } else {
            return document.querySelector(el)
        }
    }

    /**
     * Easy event listener function
     */
    const on = (type, el, listener, all = false) => {
        if (all) {
            select(el, all).forEach(e => e.addEventListener(type, listener))
        } else {
            select(el, all).addEventListener(type, listener)
        }
    }

    /**
     * Easy on scroll event listener
     */
    const onscroll = (el, listener) => {
        el.addEventListener('scroll', listener)
    }

   // Toggle sidebar and move the dashboard title
   // Function to toggle sidebar width
function toggleSidebarWidth() {
    const sidebar = document.querySelector('.sidebar');
    const main = document.querySelector('#main');
    const footer = document.querySelector('#footer');
    const logo = document.querySelector('#sidebar-logo'); 

    // Check the current width of the sidebar
    if (sidebar.style.width === '222px' || sidebar.style.width === '') {
        // If the sidebar is at 250px or uninitialized, set it to 74px
        sidebar.style.width = '80px';
        main.style.marginLeft = '100px'; // Adjust margin for main content
        footer.style.marginLeft = '100px'; // Adjust margin for footer

        // Optionally, hide span elements if required when sidebar is collapsed
        document.querySelectorAll('.sidebar-nav .nav-link span').forEach(span => {
            span.style.display = 'none';
        });
        logo.src = 'assets/img/moneyLiveIcon.png';
        logo.classList.add('expanded');


        // Adjust button and title positions
        document.querySelector('.toggle-sidebar-btn').style.left = '90px'; 
        document.querySelector('.dashboard-title').style.left = '140px'; 
    } else {
        // If the sidebar is at 74px, set it back to 250px
        sidebar.style.width = '222px';
        main.style.marginLeft = '245px'; // Reset margin for main content
        footer.style.marginLeft = '245px'; // Reset margin for footer

        // Show the span elements when the sidebar is expanded
        document.querySelectorAll('.sidebar-nav .nav-link span').forEach(span => {
            span.style.display = 'inline';
        });
        logo.src = 'assets/img/CompanyLogo.svg';
        logo.classList.remove('expanded');
        // Reset button and title positions
        document.querySelector('.toggle-sidebar-btn').style.left = '232px'; 
        document.querySelector('.dashboard-title').style.left = '300px'; 
    }
}

// Event listener to call the toggleSidebarWidth function on button click
if (document.querySelector('.toggle-sidebar-btn')) {
    document.querySelector('.toggle-sidebar-btn').addEventListener('click', toggleSidebarWidth);
}
    /**
     * Search bar toggle
     */
    if (select('.search-bar-toggle')) {
        on('click', '.search-bar-toggle', function(e) {
            select('.search-bar').classList.toggle('search-bar-show')
        })
    }

    /**
     * Navbar links active state on scroll
     */
    let navbarlinks = select('#navbar .scrollto', true)
    const navbarlinksActive = () => {
        let position = window.scrollY + 200
        navbarlinks.forEach(navbarlink => {
            if (!navbarlink.hash) return
            let section = select(navbarlink.hash)
            if (!section) return
            if (position >= section.offsetTop && position <= (section.offsetTop + section.offsetHeight)) {
                navbarlink.classList.add('active')
            } else {
                navbarlink.classList.remove('active')
            }
        })
    }
    window.addEventListener('load', navbarlinksActive)
    onscroll(document, navbarlinksActive)

    /**
     * Toggle .header-scrolled class to #header when page is scrolled
     */
    let selectHeader = select('#header')
    if (selectHeader) {
        const headerScrolled = () => {
            if (window.scrollY > 100) {
                selectHeader.classList.add('header-scrolled')
            } else {
                selectHeader.classList.remove('header-scrolled')
            }
        }
        window.addEventListener('load', headerScrolled)
        onscroll(document, headerScrolled)
    }

    /**
     * Back to top button
     */
    let backtotop = select('.back-to-top')
    if (backtotop) {
        const toggleBacktotop = () => {
            if (window.scrollY > 100) {
                backtotop.classList.add('active')
            } else {
                backtotop.classList.remove('active')
            }
        }
        window.addEventListener('load', toggleBacktotop)
        onscroll(document, toggleBacktotop)
    }

    /**
     * Initiate tooltips
     */
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    })

    /**
     * Initiate quill editors
     */
    if (select('.quill-editor-default')) {
        new Quill('.quill-editor-default', {
            theme: 'snow'
        });
    }

    if (select('.quill-editor-bubble')) {
        new Quill('.quill-editor-bubble', {
            theme: 'bubble'
        });
    }

    if (select('.quill-editor-full')) {
        new Quill(".quill-editor-full", {
            modules: {
                toolbar: [
                    [{
                        font: []
                    }, {
                        size: []
                    }],
                    ["bold", "italic", "underline", "strike"],
                    [{
                        color: []
                    },
                        {
                            background: []
                        }
                    ],
                    [{
                        script: "super"
                    },
                        {
                            script: "sub"
                        }
                    ],
                    [{
                        list: "ordered"
                    },
                        {
                            list: "bullet"
                        },
                        {
                            indent: "-1"
                        },
                        {
                            indent: "+1"
                        }
                    ],
                    ["direction", {
                        align: []
                    }],
                    ["link", "image", "video"],
                    ["clean"]
                ]
            },
            theme: "snow"
        });
    }

    /**
     * Initiate TinyMCE Editor
     */

    const useDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
    const isSmallScreen = window.matchMedia('(max-width: 1023.5px)').matches;

    tinymce.init({
        selector: 'textarea.tinymce-editor',
        plugins: 'preview importcss searchreplace autolink autosave save directionality code visualblocks visualchars fullscreen image link media codesample table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount help charmap quickbars emoticons accordion',
        editimage_cors_hosts: ['picsum.photos'],
        menubar: 'file edit view insert format tools table help',
        toolbar: "undo redo | accordion accordionremove | blocks fontfamily fontsize | bold italic underline strikethrough | align numlist bullist | link image | table media | lineheight outdent indent| forecolor backcolor removeformat | charmap emoticons | code fullscreen preview | save print | pagebreak anchor codesample | ltr rtl",
        autosave_ask_before_unload: true,
        autosave_interval: '30s',
        autosave_prefix: '{path}{query}-{id}-',
        autosave_restore_when_empty: false,
        autosave_retention: '2m',
        image_advtab: true,
        link_list: [{
            title: 'My page 1',
            value: 'https://www.tiny.cloud'
        },
            {
                title: 'My page 2',
                value: 'http://www.moxiecode.com'
            }
        ],
        image_list: [{
            title: 'My page 1',
            value: 'https://www.tiny.cloud'
        },
            {
                title: 'My page 2',
                value: 'http://www.moxiecode.com'
            }
        ],
        image_class_list: [{
            title: 'None',
            value: ''
        },
            {
                title: 'Some class',
                value: 'class-name'
            }
        ],
        importcss_append: true,
        file_picker_callback: (callback, value, meta) => {
            /* Provide file and text for the link dialog */
            if (meta.filetype === 'file') {
                callback('https://www.google.com/logos/google.jpg', {
                    text: 'My text'
                });
            }

            /* Provide image and alt text for the image dialog */
            if (meta.filetype === 'image') {
                callback('https://www.google.com/logos/google.jpg', {
                    alt: 'My alt text'
                });
            }

            /* Provide alternative source and posted for the media dialog */
            if (meta.filetype === 'media') {
                callback('movie.mp4', {
                    source2: 'alt.ogg',
                    poster: 'https://www.google.com/logos/google.jpg'
                });
            }
        },
        height: 600,
        image_caption: true,
        quickbars_selection_toolbar: 'bold italic | quicklink h2 h3 blockquote quickimage quicktable',
        noneditable_class: 'mceNonEditable',
        toolbar_mode: 'sliding',
        contextmenu: 'link image table',
        skin: useDarkMode ? 'oxide-dark' : 'oxide',
        content_css: useDarkMode ? 'dark' : 'default',
        content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:16px }'
    });

    /**
     * Initiate Bootstrap validation check
     */
    var needsValidation = document.querySelectorAll('.needs-validation')

    Array.prototype.slice.call(needsValidation)
        .forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            }

            form.classList.add('was-validated')
        }, false)
    })

    /**
     * Initiate Datatables
     */
    const datatables = select('.datatable', true)
    datatables.forEach(datatable => {
        new simpleDatatables.DataTable(datatable, {
            perPageSelect: [5, 10, 15, ["All", -1]],
            columns: [{
                select: 2,
                sortSequence: ["desc", "asc"]
            },
                {
                    select: 3,
                    sortSequence: ["desc"]
                },
                {
                    select: 4,
                    cellClass: "green",
                    headerClass: "red"
                }
            ]
        });
    })

    /**
     * Autoresize echart charts
     */
    const mainContainer = select('#main');
    if (mainContainer) {
        setTimeout(() => {
            new ResizeObserver(function() {
                select('.echart', true).forEach(getEchart => {
                    echarts.getInstanceByDom(getEchart).resize();
                })
            }).observe(mainContainer);
        }, 200);
    }

})();
//-------------------------------------------------------


//----------------- (Token html Send Api )-------------------------------------------

//---------------------------------- Activity  Api -----------------------------------
document.addEventListener('DOMContentLoaded', function() {
    const userEmail = sessionStorage.getItem('userEmail');
    const activityApiUrl = `http://localhost:8080/api/admin/distributor/AdminActivity?adminEmail=${userEmail}`;

    // Function to fetch and display activities
    function fetchAndDisplayActivities() {
        fetch(activityApiUrl)
            .then(response => response.json())
            .then(data => {
            console.log('All activities:', data); // Debugging
            const activityList = document.getElementById('activity-list');
            activityList.innerHTML = ''; // Clear existing content

            const now = new Date();

            data.forEach(activity => {
                const activityDate = new Date(activity.timestamp);
                const timeDifference = now - activityDate;
                const hoursDifference = timeDifference / (1000 * 60 * 60);

                if (hoursDifference <= 24) {
                    const timeAgo = formatTimeAgo(activityDate);

                    let badgeColor;
                    switch (activity.type) {
                        case 'RETAILER_CREATION':
                        case 'DISTRIBUTOR_CREATION':
                        case 'TOKEN_CREATION':
                            badgeColor = 'bg-sky-blue'; // Sky blue color for creation activities
                            break;
                        case 'TOKEN_SENT':
                            badgeColor = 'bg-yellow'; // Yellow color for token sent
                            break;
                        case 'DISTRIBUTOR_DELETION':
                        case 'RETAILER_DELETION':
                            badgeColor = 'bg-red'; // Red color for deletion activities
                            break;
                        default:
                            badgeColor = 'bg-muted'; // Default color
                    }

                    const activityItem = document.createElement('div');
                    activityItem.className = 'activity-item d-flex';

                    const timeLabel = document.createElement('div');
                    timeLabel.className = 'activite-label';
                    timeLabel.textContent = timeAgo;

                    const badge = document.createElement('i');
                    badge.className = `bi bi-circle-fill activity-badge ${badgeColor} align-self-start`;

                    const content = document.createElement('div');
                    content.className = 'activity-content';
                    content.innerHTML = `${activity.description}`;

                    activityItem.appendChild(timeLabel);
                    activityItem.appendChild(badge);
                    activityItem.appendChild(content);

                    activityList.appendChild(activityItem);
                }
            });
        })
            .catch(error => console.error('Error fetching activities:', error));
    }

    // Helper function to format time ago
    function formatTimeAgo(date) {
        const now = new Date();
        const diff = now - date;
        const minutes = Math.floor(diff / (1000 * 60));
        const hours = Math.floor(diff / (1000 * 60 * 60));

        if (hours < 1) return `${minutes} min${minutes > 1 ? 's' : ''} ago`;
        return `${hours} hr${hours > 1 ? 's' : ''} ago`;
    }

    // Fetch and display activities
    fetchAndDisplayActivities();
});
//----------------------------------User name Api -----------------------------------

document.addEventListener("DOMContentLoaded", function() {
    // Function to fetch the user's name from the API
    function fetchUserName() {
        // Get the email from session data
        const email = sessionStorage.getItem('userEmail'); // Ensure this is how you fetch email from the session

        if (email) {
            fetch(`http://localhost:8080/api/admin/distributor/name?email=${encodeURIComponent(email)}`)
                .then(response => response.json())
                .then(data => {
                const userName = data.name || 'Guest'; // Use 'Guest' if no name is found
                // Update the HTML elements with the fetched name
                document.querySelector('.nav-profile .dropdown-toggle').textContent = userName;
                document.querySelector('.dropdown-header h6').textContent = userName;
            })
                .catch(error => {
                console.error('Error fetching name:', error);
                // Fallback to 'Guest' in case of an error
                document.querySelector('.nav-profile .dropdown-toggle').textContent = 'Guest';
                document.querySelector('.dropdown-header h6').textContent = 'Guest';
            });
        } else {
            console.error('No email found in session.');
            // Fallback to 'Guest' if email is missing
            document.querySelector('.nav-profile .dropdown-toggle').textContent = 'Guest';
            document.querySelector('.dropdown-header h6').textContent = 'Guest';
        }
    }

    // Call the function on page load
    fetchUserName();
});

//----------------------------------Fetch count creators retailer  Api ----------------------------------
document.addEventListener('DOMContentLoaded', function() {
    const adminEmail = sessionStorage.getItem('userEmail');

    if (adminEmail) {
        // Fetch the list of distributors
        fetch(`http://localhost:8080/api/admin/distributor/list?adminEmail=${encodeURIComponent(adminEmail)}`)
            .then(response => response.json())
            .then(data => {
            // Assuming data is an array of distributors
            const distributors = data || [];

            // Calculate the number of distributors
            const distributorCount = distributors.length;

            // Update the sales card with the distributor count
            document.getElementById('distributorCount').textContent = distributorCount;
        })
            .catch(error => {
            console.error('Error fetching distributor count:', error);
            // Handle error case, possibly display 0
            document.getElementById('distributorCount').textContent = '0';
        });
    } else {
        console.error('Admin email is not found in session storage.');
        // Handle case where adminEmail is not present
        document.getElementById('distributorCount').textContent = '0';
    }
});
//----------------------------------total Token  sales api  ----------------------------------
document.addEventListener("DOMContentLoaded", function() {
    // Assuming you have the userEmail stored in session storage
    const userEmail = sessionStorage.getItem('userEmail');

    if (userEmail) {
        // Step 1: Fetch user info
        fetch(`http://localhost:8080/api/admin/distributor/userInfo?email=${userEmail}`)
            .then(response => response.json())
            .then(userInfo => {
            const phoneNumber = userInfo.phoneNumber;

            if (phoneNumber) {
                // Step 2: Fetch total transferred tokens using the phone number
                fetch(`http://localhost:8080/api/admin/token/transferred/total?senderIdentifier=${phoneNumber}`)
                    .then(response => response.json())
                    .then(tokenInfo => {
                    const totalTransferredTokens = tokenInfo.totalTransferredTokens;

                    // Step 3: Update the frontend
                    document.getElementById("Tokensales").textContent = totalTransferredTokens;
                })
                    .catch(error => console.error('Error fetching token transfer data:', error));
            } else {
                console.error('Phone number not found in user info.');
            }
        })
            .catch(error => console.error('Error fetching user info:', error));
    } else {
        console.error('User email not found in session storage.');
    }
});


//----------------------------------Logout Api ----------------------------------

  document.addEventListener("DOMContentLoaded", function() {
    // Add event listener to the logout button
    document.getElementById('logoutBtn').addEventListener('click', function(event) {
      event.preventDefault(); // Prevent the default link behavior
      sessionStorage.clear(); // Clear session storage
      window.location.href = '/login.html'; // Redirect to login page or any other page
    });
  });