import {
  locations,
  ages,
  genders,
  tags
} from "$lib/types/filterStores";

const serverEndPoint = "https://localhost:8443/server/filter";

async function fetchCheckboxItems(endpoint: string) {
  const response = await fetch(endpoint);
  const labels: string[] = await response.json();

  return labels.map((label, index) => ({
    id: index + 1,
    label,
    checked: false
  }));
}

export async function loadFilterStores() {
  locations.set(await fetchCheckboxItems(serverEndPoint + "/locations"));
  ages.set(await fetchCheckboxItems(serverEndPoint + "/ages"));
  genders.set(await fetchCheckboxItems(serverEndPoint + "/genders"));
  tags.set(await fetchCheckboxItems(serverEndPoint + "/tags"));
}
