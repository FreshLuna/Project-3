import { dateFormatter } from '$lib/utils/DateFormatter'; 
export async function loadActivities() {
    const res = await fetch('https://localhost:8443/server/activities');
    if (!res.ok) throw new Error('Failed to load activities');

    return res.json().then((data) =>
        data.map((item: any) => ({


            
            id: item.ActivityID,
            title: item.ActivityName,
            organization: item.ActivityOrganizer,
            type: item.TypeOfActivity,
            instructors: item.Instructors,
            date: item.DateAndTime,
            location: item.Location,
            genderGroup: item.GenderGroup,
            age: item.AgeGroup,
            capacity: item.ActivityCapacity,
            waitingListCapacity: item.WaitingListCapacity,
            waitingListEnabled: item.WaitingListEnabled,
            description: item.ActivityDescription,
            difficulty: item.ActivityDifficulty,
            tags: item.Tags ?? [],
            imgUrl: item.ImgUrl,
            formattedDate: dateFormatter(item.DateAndTime)
        }))
    );
}
