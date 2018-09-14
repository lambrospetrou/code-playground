import * as m from 'mithril'
// @ts-ignore
import pipe from 'ramda/es/pipe'

import { createActions } from './actions'
import { createAppModel, AppModel, UpdateStream, Actions } from './models'
import { App } from './components'

const genView = (actions: Actions) => (model: AppModel) =>
  App.view(actions, model)

const sideEffectDummy = (model: AppModel) =>
  console.info('sideEffectDummy', JSON.stringify(model))

export const createApp = (update: UpdateStream) => {
  const actions = createActions(update)

  // Transform the received model before passing it to the view.
  const transformations = pipe(
    ...[
      genView(actions) // Has to be the last one!
    ]
  )

  // Side effects using the updated model that are not view related.
  // e.g. updating the window.location from the updated model.
  const sideEffects = [sideEffectDummy]

  const listeners = (m: AppModel) => {
    sideEffects.forEach(l => l(m))
    return transformations(m)
  }

  return {
    model: () => ({ ...createAppModel() }),

    render: (container: HTMLElement, v: m.Vnode) => m.render(container, v),

    view: listeners
  }
}
