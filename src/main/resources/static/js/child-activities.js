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
                activitiesList.innerHTML = `
                    <div class="empty-state">
                        <div class="empty-state-icon">📝</div>
                        <h3 class="empty-state-title">No activities recorded yet</h3>
                        <p class="empty-state-message">Start tracking your child's activities by adding the first one.</p>
                    </div>
                `;
                return;
            }
            
            activities.forEach(activity => {
                const activityElement = document.createElement('div');
                activityElement.className = 'activity-item';
                
                let diaperInfo = '';
                if (activity.activityType === 'DIAPER' && activity.diaperCondition) {
                    diaperInfo = `<div class="activity-notes">Diaper Condition: ${activity.diaperCondition}</div>`;
                }
                
                const startTime = new Date(activity.startTime).toLocaleString();
                const endTime = activity.endTime ? new Date(activity.endTime).toLocaleString() : 'Ongoing';
                const notes = activity.notes ? `<div class="activity-notes">${activity.notes}</div>` : '';
                
                activityElement.innerHTML = `
                    <div class="activity-info">
                        <div class="activity-type">${activity.activityType}</div>
                        <div class="activity-time">${startTime} - ${endTime}</div>
                        ${diaperInfo}
                        ${notes}
                    </div>
                    <div class="activity-actions">
                        <button class="edit-activity-btn" onclick="editActivity(${JSON.stringify(activity).replace(/"/g, '&quot;')})">Edit</button>
                        <button class="delete-activity-btn" onclick="deleteActivity(${activity.id})">Delete</button>
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
    // Populate the edit form with activity data
    document.getElementById('editActivityId').value = activity.id;
    document.getElementById('editActivityType').value = activity.activityType;
    document.getElementById('editStartTime').value = formatDateTimeForInput(activity.startTime);
    document.getElementById('editEndTime').value = activity.endTime ? formatDateTimeForInput(activity.endTime) : '';
    document.getElementById('editNotes').value = activity.notes || '';
    
    // Handle diaper condition
    if (activity.activityType === 'DIAPER') {
        document.getElementById('editDiaperConditionContainer').style.display = 'block';
        document.getElementById('editDiaperCondition').value = activity.diaperCondition || '';
        document.getElementById('editDiaperCondition').required = true;
    } else {
        document.getElementById('editDiaperConditionContainer').style.display = 'none';
        document.getElementById('editDiaperCondition').required = false;
        document.getElementById('editDiaperCondition').value = '';
    }

    // Show the modal
    document.getElementById('editActivityModal').style.display = 'flex';
}

function formatDateTimeForInput(dateTimeString) {
    const date = new Date(dateTimeString);
    return date.toISOString().slice(0, 16); // Format: YYYY-MM-DDTHH:mm
}

function updateActivity() {
    const activityId = document.getElementById('editActivityId').value;
    const activityData = {
        id: activityId,
        activityType: document.getElementById('editActivityType').value,
        startTime: document.getElementById('editStartTime').value,
        endTime: document.getElementById('editEndTime').value || null,
        notes: document.getElementById('editNotes').value,
        diaperCondition: document.getElementById('editDiaperCondition').value,
        child: {
            id: document.getElementById('childId').value
        }
    };

    fetch(`/api/activities/${activityId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(activityData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        // Close the modal
        closeEditModal();
        // Reload activities
        loadActivities();
    })
    .catch(error => {
        console.error('Error updating activity:', error);
        alert('Error updating activity. Please try again.');
    });
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

function closeEditModal() {
    document.getElementById('editActivityModal').style.display = 'none';
}

function toggleEditDiaperCondition() {
    const activityType = document.getElementById('editActivityType').value;
    const diaperConditionContainer = document.getElementById('editDiaperConditionContainer');
    
    if (activityType === 'DIAPER') {
        diaperConditionContainer.style.display = 'block';
        document.getElementById('editDiaperCondition').required = true;
    } else {
        diaperConditionContainer.style.display = 'none';
        document.getElementById('editDiaperCondition').required = false;
        document.getElementById('editDiaperCondition').value = '';
    }
} 