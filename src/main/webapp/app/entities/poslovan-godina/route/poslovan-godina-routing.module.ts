import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PoslovanGodinaComponent } from '../list/poslovan-godina.component';
import { PoslovanGodinaDetailComponent } from '../detail/poslovan-godina-detail.component';
import { PoslovanGodinaUpdateComponent } from '../update/poslovan-godina-update.component';
import { PoslovanGodinaRoutingResolveService } from './poslovan-godina-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const poslovanGodinaRoute: Routes = [
  {
    path: '',
    component: PoslovanGodinaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PoslovanGodinaDetailComponent,
    resolve: {
      poslovanGodina: PoslovanGodinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PoslovanGodinaUpdateComponent,
    resolve: {
      poslovanGodina: PoslovanGodinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PoslovanGodinaUpdateComponent,
    resolve: {
      poslovanGodina: PoslovanGodinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(poslovanGodinaRoute)],
  exports: [RouterModule],
})
export class PoslovanGodinaRoutingModule {}
