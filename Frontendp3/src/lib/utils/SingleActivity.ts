export async function loadActivity(thing: number) {
    const res = await fetch(`https://localhost:8443/server/activities/${thing}`);
    if (!res.ok) throw new Error('Failed to load activities');

    const data = await res.json();
return {
    id: data.ActivityID,
    title: data.ActivityName,
    organization: data.ActivityOrganizer,
    type: data.TypeOfActivity,
    instructors: data.Instructors,
    date: data.DateAndTime,
    location: data.Location,
    genderGroup: data.GenderGroup,
    age: data.AgeGroup,
    capacity: data.ActivityCapacity,
    waitingListCapacity: data.WaitingListCapacity,
    waitingListEnabled: data.WaitingListEnabled,
    description: data.ActivityDescription,
    difficulty: data.ActivityDifficulty,
    tags: data.Tags ?? [],
    imgUrl: data.ImgUrl
};
}