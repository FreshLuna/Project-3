import { writable, type Writable } from "svelte/store";

export interface Filters {
  locations: string[];
  weekdays: string[];
  ages: string[];
  genders: string[];
  tags: string[];
}

// for all checkbox items
export interface CheckboxItem {
    id: number;
    label: string;
    checked: boolean;
}

let serverEndPoint = "https://localhost:8443/server/filter"


async function fetchCheckboxItems(endpoint: string): Promise<CheckboxItem[]> {
    try {
        const response = await fetch(endpoint);
        if (!response.ok) throw new Error(`Failed to fetch ${endpoint}`);
        
        // Backend returns an array of labels
        const labels: string[] = await response.json();

        // Map them to CheckboxItems with default checked=false and automatic ids
        return labels.map((label, index) => ({
            id: index + 1,  // auto-increment ID
            label,
            checked: false  // default to unchecked
        }));
    } catch (error) {
        console.error(error);
        return [];
    }
}

export const locations = writable<CheckboxItem[]>([]);
//export const weekdays = writable<CheckboxItem[]>([]);
export const ages = writable<CheckboxItem[]>([]);
export const genders = writable<CheckboxItem[]>([]);
export const tags = writable<CheckboxItem[]>([]);

export async function loadStores() {
    locations.set(await fetchCheckboxItems(serverEndPoint + "/locations"));
    //weekdays.set(await fetchCheckboxItems(serverEndPoint + "/weekdays"));
    ages.set(await fetchCheckboxItems(serverEndPoint + "/ages"));
    genders.set(await fetchCheckboxItems(serverEndPoint + "/genders"));
    tags.set(await fetchCheckboxItems(serverEndPoint + "/tags"));
}

// // ----- LOCATION STORE -----
// export const locations: Writable<CheckboxItem[]> = writable([
//     { id: 1, label: "Aalborg Centrum", checked: false },
//     { id: 2, label: "Aalborg Øst", checked: false },
//     { id: 3, label: "Hasseris", checked: false },
//     { id: 4, label: "Skalborg", checked: false },
//     { id: 5, label: "Gug", checked: false },
//     { id: 6, label: "Aalborg Vestby", checked: false }
// ]);

// ----- WEEKDAY STORE -----
export const weekdays: Writable<CheckboxItem[]> = writable([
    { id: 1, label: "Mandag", checked: false },
    { id: 2, label: "Tirsdag", checked: false },
    { id: 3, label: "Onsdag", checked: false },
    { id: 4, label: "Torsdag", checked: false },
    { id: 5, label: "Fredag", checked: false },
    { id: 6, label: "Lørdag", checked: false },
    { id: 7, label: "Søndag", checked: false }
]);

// // ----- AGE STORE -----
// export const ages: Writable<CheckboxItem[]> = writable([
//     { id: 1, label: "Alle aldre", checked: false },
//     { id: 2, label: "12+", checked: false },
//     { id: 3, label: "15+", checked: false },
//     { id: 4, label: "18+", checked: false },
//     { id: 5, label: "21+", checked: false },
//     { id: 6, label: "25+", checked: false },
//     { id: 7, label: "30+", checked: false }
// ]);

// // ----- GENDER STORE -----
// export const genders: Writable<CheckboxItem[]> = writable([
//     { id: 1, label: "Alle", checked: false },
//     { id: 2, label: "Drenge/mænd", checked: false },
//     { id: 3, label: "Piger/kvinder", checked: false }
// ]);

// // ----- TAG STORE -----
// export const tags: Writable<CheckboxItem[]> = writable([
//     { id: 1, label: "Kampsport", checked: false },
//     { id: 2, label: "Vand", checked: false },
//     { id: 3, label: "Ketchersport", checked: false }
// ]);