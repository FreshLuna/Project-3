export interface Activity {
    id: number;
    title: string;
    organization: string;

    type: string;
    instructors: string;

    date: number;

    location: string;

    genderGroup: string | null;
    age: string | null;

    capacity: number;
    waitingListCapacity: number;
    waitingListEnabled: boolean;

    description: string;
    difficulty: string | null;

    tags: string[];

    imgUrl?: string;

    participantCount: number;

    formattedDate: string;
}

export function mapActivity(item: any): Activity {
    return {
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
        participantCount: item.ParticipantCount,
        formattedDate: dateFormatter(item.DateAndTime)
    };
}

export function dateFormatter(input?: string | number): string {
    if (!input) return 'Invalid date';

    const date = input.toString();

    // Make sure the string is long enough
    if (date.length < 12) return 'Invalid date';

    const year = date.slice(0, 4);    
    const month = date.slice(4, 6);   
    const day = date.slice(6, 8);     
    const hour = date.slice(8, 10);   
    const minute = date.slice(10, 12);

    return `${day}/${month}/${year} ${hour}:${minute}`;
}


