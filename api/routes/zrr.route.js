import { Router } from 'express';
import { getZrr } from '../services/zrr.js';
const router = new Router();
 
router.get('/', async (req, res, next) => {
  let options = { 
  };


  try {
    const result = await getZrr(options);
    res.status(result.status || 200).send(result.data);
  }
  catch (err) {
    return res.status(500).send({
      error: err || 'Something went wrong.'
    });
  }
});

export default router;