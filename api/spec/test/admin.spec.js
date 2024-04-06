import { spawnFlask } from "../../services/admin.js";
import { getAllresources } from "../../services/resources.js";

describe('spawnFlask', () => {
    it('vérifie si on spawnFlask', async () => {
        await spawnFlask({
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
        })
        expect(true).toBe(true);
    });
});

describe('readFalsk', () => {
    it('lit le data.json et trouve la flaskTest', async () => {
        const allRessources = await getAllresources();
        const flaskTest = allRessources.find(obj => obj.id === "potionTest");
        expect(flaskTest).toBeDefined();
    });
});
