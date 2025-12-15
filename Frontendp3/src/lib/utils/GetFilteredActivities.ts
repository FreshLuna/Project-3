import { writable, derived } from 'svelte/store';
import type { Activity } from '$lib/types/Activities';
import type { CheckboxItem } from '$lib/types/filterStores';

export const activities = writable<Activity[]>([]);
export const solsikke = writable<CheckboxItem>({ id: 0, label: "Solsikke", checked: false });
export const waitinglist = writable<CheckboxItem>({ id: 0, label: "Venteliste", checked: false });

// Derived store for filtered activities
export const filteredActivities = derived(
  [activities, solsikke, waitinglist],
  ([$activities, $solsikke, $waitinglist]) =>
    $activities.filter(a => {
      const waitingCondition = $waitinglist.checked ? a.participantCount > 0 : true;
      const solsikkeCondition = $solsikke.checked ? a.tags?.includes("Solsikke") : true;
      return waitingCondition && solsikkeCondition;
    })
);
