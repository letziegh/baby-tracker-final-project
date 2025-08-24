// Set current datetime as default for start time
document.addEventListener('DOMContentLoaded', function() {
    const now = new Date();
    const localDateTime = new Date(now.getTime() - now.getTimezoneOffset() * 60000).toISOString().slice(0, 16);
    document.getElementById('startTime').value = localDateTime;
    
    // Add activity form submission
    document.getElementById('addActivityForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const activityType = document.getElementById('activityType').value;
        const startTime = document.getElementById('startTime').value;
        const endTime = document.getElementById('endTime').value;
        const notes = document.getElementById('notes').value;
        const diaperCondition = document.getElementById('diaperCondition').value;
        const childId = document.getElementById('childId').value;
        
        const activityData = {
            activityType: activityType,
            startTime: startTime,
            endTime: endTime,
            notes: notes,
            diaperCondition: diaperCondition,
            child: {
                id: childId
            }
        };
        
        fetch('/api/activities', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(activityData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Show success message
            alert('Activity added successfully!');
            
            // Redirect to child activities page
            window.location.href = `/api/children/${childId}/activities`;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error adding activity. Please try again.');
        });
    });
});

function toggleDiaperCondition() {
    const activityType = document.getElementById('activityType').value;
    const diaperConditionContainer = document.getElementById('diaperConditionContainer');
    
    if (activityType === 'DIAPER') {
        diaperConditionContainer.style.display = 'block';
        document.getElementById('diaperCondition').required = true;
    } else {
        diaperConditionContainer.style.display = 'none';
        document.getElementById('diaperCondition').required = false;
        document.getElementById('diaperCondition').value = '';
    }
}
