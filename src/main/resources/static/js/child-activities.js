document.addEventListener('DOMContentLoaded', function() {
    // Get child ID from Thymeleaf
    const childId = document.getElementById('childId').value;
    console.log('DOM loaded, childId:', childId);
    
    if (!childId) {
        console.error('Child ID not found in the page');
        document.getElementById('activitiesList').innerHTML = 
            '<div class="alert alert-danger">Error: Child ID not found. Please try again.</div>';
        return;
    }
    
    loadActivities();
});

function loadActivities() {
    const childId = document.getElementById('childId').value;
    console.log('Fetching activities for childId:', childId);
    fetch(`/api/activities/child/${childId}`)
        .then(response => {
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error(`Network response was not ok: ${response.status} ${response.statusText}`);
            }
            return response.json();
        })
        .then(activities => {
            console.log('Received activities:', activities);
            const activitiesList = document.getElementById('activitiesList');
            activitiesList.innerHTML = '';
            
            if (activities.length === 0) {
                activitiesList.innerHTML = '<div class="list-group-item">No activities recorded yet.</div>';
                return;
            }
            
            activities.forEach(activity => {
                const activityElement = document.createElement('div');
                activityElement.className = 'list-group-item';
                activityElement.innerHTML = `
                    <h4>${activity.activityType}</h4>
                    <p>Start Time: ${new Date(activity.startTime).toLocaleString()}</p>
                    <p>End Time: ${activity.endTime ? new Date(activity.endTime).toLocaleString() : 'Ongoing'}</p>
                    <p>Notes: ${activity.notes || 'No notes'}</p>
                    <div class="btn-group">
                        <button class="btn btn-warning btn-sm me-2" onclick="editActivity(${JSON.stringify(activity).replace(/"/g, '&quot;')})">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteActivity(${activity.id})">Delete</button>
                    </div>
                `;
                activitiesList.appendChild(activityElement);
            });
        })
        .catch(error => {
            console.error('Error loading activities:', error);
            document.getElementById('activitiesList').innerHTML = 
                `<div class="alert alert-danger">Error loading activities: ${error.message}</div>`;
        });
}

function editActivity(activity) {
    // Implement edit functionality
    alert('Edit functionality to be implemented');
}

function deleteActivity(activityId) {
    if (confirm('Are you sure you want to delete this activity?')) {
        fetch(`/api/activities/${activityId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            loadActivities(); // Reload the activities list
        })
        .catch(error => {
            console.error('Error deleting activity:', error);
            alert('Error deleting activity. Please try again.');
        });
    }
} 