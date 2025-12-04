import { writable } from "svelte/store";

export const selectedTags = writable<string[]>([]);
