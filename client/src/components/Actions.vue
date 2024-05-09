<script async setup>
  let joueurJohn = {
    "id": "John",
    "position": [
        "45.78216635696405",
        "4.864507913589478"
    ],
    "role": "VILLAGEOIS",
    "ttl": 20,
    "potions": 0,
    "terminated": 0,
    "turned": 0
  }
  let ressource = [
    {
        "id": "toto",
        "position": [
            "45.78196433811854",
            "4.865881204605103"
        ],
        "role": "PIRATE",
        "ttl": 0,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "John",
        "position": [
            "45.78216635696405",
            "4.864507913589478"
        ],
        "role": "VILLAGEOIS",
        "ttl": 20,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "titi",
        "position": [
            "45.78282104245197",
            "4.864250421524049"
        ],
        "role": "PIRATE",
        "ttl": 0,
        "potions": 0,
        "terminated": 10,
        "turned": 0
    },
    {
        "id": "potion1",
        "position": [
            "45.78220619944917",
            "4.865867793560029"
        ],
        "role": "FLASK",
        "ttl": 50,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "potion2",
        "position": [
            "45.78163137968618",
            "4.865838289260865"
        ],
        "role": "FLASK",
        "ttl": 60,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "potion3",
        "position": [
            "45.781825917472084",
            "4.868005514144898"
        ],
        "role": "FLASK",
        "ttl": 60,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "potion4",
        "position": [
            45.78276866789585,
            4.868021607398988
        ],
        "role": "FLASK",
        "ttl": 60,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "potion5",
        "position": [
            45.78346075627386,
            4.866701960563661
        ],
        "role": "FLASK",
        "ttl": "20",
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "ADMIN",
        "position": [
            "45.78159022737526",
            "4.86721158027649"
        ],
        "role": "ADMIN",
        "ttl": 0,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "potion6",
        "position": [
            45.78114877340201,
            4.866026043891908
        ],
        "role": "FLASK",
        "ttl": 60,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "potionTest",
        "position": [
            "45.78220619944917",
            "4.865867793560029"
        ],
        "role": "FLASK",
        "ttl": 50,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "potionTest",
        "position": [
            "45.78220619944917",
            "4.865867793560029"
        ],
        "role": "FLASK",
        "ttl": 50,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    },
    {
        "id": "potionTest",
        "position": [
            "45.78220619944917",
            "4.865867793560029"
        ],
        "role": "FLASK",
        "ttl": 50,
        "potions": 0,
        "terminated": 0,
        "turned": 0
    }
  ];
  
  const L = await import("leaflet");

  function aTuer(id) {
    ressource = ressource.filter(item => item.id !== id);
    console.log(ressource)
  }

  function aConvert(id) {
    const r = ressource.find(item => item.id === id);
    r.role = 'PIRATE';
    console.log(ressource);
  }

  function aBoire(id) {
    const flask = ressource.find(item => item.id === id);
    joueurJohn.ttl += flask.ttl;
    ressource = ressource.filter(item => item.id !== id);
    console.log(ressource);
  }

  function isNearFromMe(position) {
    const posMe = L.latLng(joueurJohn.position);
    const posOther = L.latLng(position);
    const dist = posMe.distanceTo(posOther);
    console.log(dist <= 100);
    return dist <= 100;
  }
</script>

<template>
  <section :key="forceReload">
    <h2>actions</h2>
    <br>
    <div v-for="r in ressource">
      <span v-if="isNearFromMe(r.position)">
        {{ r.id }}, {{ r.role }}
        <button @click=aTuer(r.id) v-if="r.role === 'PIRATE'">tuer</button>
        <button @click=aConvert(r.id) v-else-if="r.role === 'VILLAGEOIS'">convertir</button>
        <button @click=aBoire(r.id) v-else-if="r.role === 'FLASK'">boire</button>
        <br>
      </span>
    </div>
  </section>
</template>