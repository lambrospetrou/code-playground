import { UpdateStream } from '../models'
import { createActions } from './index'

describe('inc', () => {
  it('should increment the counter in the model with the given value', () => {
    const updateStream = jest.fn()
    const { inc } = createActions((updateStream as any) as UpdateStream)

    inc(11)

    const initialState = { counter: 10 }
    const updateModel = updateStream.mock.calls[0][0]
    expect(updateModel(initialState)).toEqual({ counter: 21 })
  })
})
