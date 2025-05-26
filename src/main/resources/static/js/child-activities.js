// Get child ID from Thymeleaf
const childId = document.getElementById('childId').value;

$(document).ready(function() {
    loadActivities();
});

function loadActivities() {
    $.get('/api/activities/child/' + childId, function(activities) {
        const activitiesList = $('#activitiesList');
        activitiesList.empty();
        
        activities.forEach(function(activity) {
            const activityElement = $('<div class="list-group-item">')
                .append($('<h4>').text(activity.activityType))
                .append($('<p>').text('Start Time: ' + new Date(activity.startTime).toLocaleString()))
                .append($('<p>').text('End Time: ' + (activity.endTime ? new Date(activity.endTime).toLocaleString() : 'Ongoing')))
                .append($('<p>').text('Notes: ' + (activity.notes || 'No notes')))
                .append($('<div class="btn-group">')
                    .append($('<button class="btn btn-warning btn-sm me-2">')
                        .text('Edit')
                        .click(function() { editActivity(activity); }))
                    .append($('<button class="btn btn-danger btn-sm">')
                        .text('Delete')
                        .click(function() { deleteActivity(activity.id); })));
            
            activitiesList.append(activityElement);
        });
    });
}

function editActivity(activity) {
    // Implement edit functionality
    // You can either show a modal or redirect to an edit page
    alert('Edit functionality to be implemented');
}

function deleteActivity(activityId) {
    if (confirm('Are you sure you want to delete this activity?')) {
        $.ajax({
            url: '/api/activities/' + activityId,
            type: 'DELETE',
            success: function() {
                loadActivities();
            },
            error: function(xhr) {
                alert('Error deleting activity: ' + xhr.responseText);
            }
        });
    }
} 