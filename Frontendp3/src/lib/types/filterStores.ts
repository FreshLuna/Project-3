import { writable, type Writable } from "svelte/store";
export interface Filters { 
    locations: string[]; 
    weekdays: string[]; 
    ages: string[]; 
    genders: string[]; 
    tags: string[]; }

export interface CheckboxItem {
  id: number;
  label: string;
  checked: boolean;
}
export function clearAllFilters() { 
    locations.update(list => list.map(i => ({ ...i, checked: false }))); 
    weekdays.update(list => list.map(i => ({ ...i, checked: false }))); 
    ages.update(list => list.map(i => ({ ...i, checked: false }))); 
    genders.update(list => list.map(i => ({ ...i, checked: false }))); 
    tags.update(list => list.map(i => ({ ...i, checked: false }))); }

export const locations = writable<CheckboxItem[]>([]);
export const ages = writable<CheckboxItem[]>([]);
export const genders = writable<CheckboxItem[]>([]);
export const tags = writable<CheckboxItem[]>([]);
export const weekdays: Writable<CheckboxItem[]> = writable([

  { id: 1, label: "Mandag", checked: false },
  { id: 2, label: "Tirsdag", checked: false },
  { id: 3, label: "Onsdag", checked: false },
  { id: 4, label: "Torsdag", checked: false },
  { id: 5, label: "Fredag", checked: false },
  { id: 6, label: "Lørdag", checked: false },
  { id: 7, label: "Søndag", checked: false }
]);