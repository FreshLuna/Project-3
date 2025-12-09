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

    imgUrl?: string | null;

    formattedDate: string;
}
