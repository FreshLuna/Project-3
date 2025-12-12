import { mapActivity } from '$lib/types/Activities';

export async function loadActivities(api: string, body?: any) {
    const options: RequestInit = body
        ? {
              method: "POST",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify(body),
          }
        : {
              method: "GET",
          };
          
    const res = await fetch(`https://localhost:8443/server/${api}`, options);

    if (!res.ok) throw new Error(`Failed to load ${api}`);

    const data = await res.json();

    const list = Array.isArray(data) ? data : [data];

    return list.map(mapActivity);

}
export async function loadActivity(thing: number) {
    const res = await fetch(`https://localhost:8443/server/activities/${thing}`);
    if (!res.ok) throw new Error('Failed to load activities');

    const data = await res.json();

    return mapActivity(data);
}

