import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PrometMagacinskeKarticeComponent } from '../list/promet-magacinske-kartice.component';
import { PrometMagacinskeKarticeDetailComponent } from '../detail/promet-magacinske-kartice-detail.component';
import { PrometMagacinskeKarticeUpdateComponent } from '../update/promet-magacinske-kartice-update.component';
import { PrometMagacinskeKarticeRoutingResolveService } from './promet-magacinske-kartice-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const prometMagacinskeKarticeRoute: Routes = [
  {
    path: '',
    component: PrometMagacinskeKarticeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrometMagacinskeKarticeDetailComponent,
    resolve: {
      prometMagacinskeKartice: PrometMagacinskeKarticeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrometMagacinskeKarticeUpdateComponent,
    resolve: {
      prometMagacinskeKartice: PrometMagacinskeKarticeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrometMagacinskeKarticeUpdateComponent,
    resolve: {
      prometMagacinskeKartice: PrometMagacinskeKarticeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(prometMagacinskeKarticeRoute)],
  exports: [RouterModule],
})
export class PrometMagacinskeKarticeRoutingModule {}
